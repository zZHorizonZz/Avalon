package com.github.avalon.server;

import com.github.avalon.account.handler.LoginHandler;
import com.github.avalon.annotation.AnnotationManager;
import com.github.avalon.chat.ChatHandler;
import com.github.avalon.chat.ChatManager;
import com.github.avalon.common.system.UtilSecurity;
import com.github.avalon.concurrent.ConcurrentManager;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.descriptor.DescriptorManager;
import com.github.avalon.dimension.DimensionManager;
import com.github.avalon.editor.EditManager;
import com.github.avalon.initialization.InitializationManager;
import com.github.avalon.item.ItemManager;
import com.github.avalon.network.PlayerSessionContainer;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.network.SocketServer;
import com.github.avalon.network.protocol.ProtocolContainer;
import com.github.avalon.network.protocol.ProtocolRegistry;
import com.github.avalon.packet.PacketManager;
import com.github.avalon.player.handler.BlockHandler;
import com.github.avalon.player.handler.MovementHandler;
import com.github.avalon.scheduler.SchedulerManager;
import com.github.avalon.server.command.StopCommand;
import com.github.avalon.timer.Timer;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;

/**
 * Server is basic implementation of of the {@link IServer} and {@link ServerRunner}.
 *
 * @version 1.0
 * @author Horizon
 */
public class Server implements IServer, ServerRunner {

  public static final DefaultLogger LOGGER = new DefaultLogger(Server.class);

  private static ServerData serverData = new ServerData();

  private final ServerVersion serverVersion = new ServerVersion();

  private final Bootstrap bootstrap;
  private final ServerThread serverThread;
  private KeyPair securityKey;

  private final ProtocolContainer protocolContainer;
  private SocketServer socketServer;

  private ServerState serverState;

  private final InitializationManager initializationManager;

  private final ConcurrentManager concurrentManager;
  private PacketManager packetManager;
  private SchedulerManager schedulerManager;
  private DescriptorManager descriptorManager;
  private DimensionManager dimensionManager;
  private ChatManager chatManager;
  private ItemManager itemManager;
  private EditManager editManager;

  private final PlayerSessionContainer playerSessionRegistry;

  public Server(Bootstrap bootstrap) {
    this.bootstrap = bootstrap;

    concurrentManager = new ConcurrentManager(this);
    initializationManager = new InitializationManager(this);

    serverState = ServerState.STARTING;

    protocolContainer = new ProtocolContainer(this);
    playerSessionRegistry = new PlayerSessionContainer();

    try {
      securityKey = UtilSecurity.generateKeyPair();
    } catch (NoSuchAlgorithmException exception) {
      exception.printStackTrace(); // Interupt
    }

    serverThread = new ServerThread(this);
  }

  @Override
  public void startServer() {
    Timer startTimer = new Timer("Start Timer", false);
    startTimer.start();

    LOGGER.info("          /$$$$$$                      /$$");
    LOGGER.info("         /$$__  $$                    | $$");
    LOGGER.info("        | $$  \\ $$ /$$    /$$ /$$$$$$ | $$  /$$$$$$  /$$$$$$$");
    LOGGER.info("        | $$$$$$$$|  $$  /$$/|____  $$| $$ /$$__  $$| $$__  $$");
    LOGGER.info("        | $$__  $$ \\  $$/$$/  /$$$$$$$| $$| $$  \\ $$| $$  \\ $$");
    LOGGER.info("        | $$  | $$  \\  $$$/  /$$__  $$| $$| $$  | $$| $$  | $$");
    LOGGER.info("        | $$  | $$   \\  $/  |  $$$$$$$| $$|  $$$$$$/| $$  | $$");
    LOGGER.info("        |__/  |__/    \\_/    \\_______/|__/ \\______/ |__/  |__/");

    if (serverData.isDeveloperMode()) {
      LOGGER.info(
          "This server is currently running in developer mode and should be used only for develop purposes and nothing else!");
    }
    LOGGER.info("Loading server...");

    packetManager = initializationManager.registerManager(new PacketManager(this));
    schedulerManager = initializationManager.registerManager(new SchedulerManager(this));
    descriptorManager = initializationManager.registerManager(new DescriptorManager(this));
    dimensionManager = initializationManager.registerManager(new DimensionManager(this));
    chatManager = initializationManager.registerManager(new ChatManager(this));
    itemManager = initializationManager.registerManager(new ItemManager(this));
    editManager = initializationManager.registerManager(new EditManager(this));

    AnnotationManager.ANNOTATION_TASK_EXECUTOR.initialize(
        new DefaultThreadFactory("Annotation_Executor"));

    bindServer();

    if (serverThread != null) {
      serverThread.start();
    } else {
      shutdown();
    }

    packetManager.registerPacketListener(new MovementHandler());
    packetManager.registerPacketListener(new BlockHandler());
    packetManager.registerPacketListener(new ChatHandler());
    packetManager.registerPacketListener(new LoginHandler());

    chatManager.registerCommands(new StopCommand());

    LOGGER.info("Server has been successfully loaded. In %s seconds.", startTimer.stop() / 1000);
  }

  @Override
  public void stopServer() {
    socketServer.shutdown();
  }

  @Override
  public void bindServer() {
    LOGGER.info("Loading socket server...");

    protocolContainer.addProtocolRegistry(new ProtocolRegistry(ProtocolType.HANDSHAKE));
    protocolContainer.addProtocolRegistry(new ProtocolRegistry(ProtocolType.LOGIN));
    protocolContainer.addProtocolRegistry(new ProtocolRegistry(ProtocolType.PLAY));
    protocolContainer.addProtocolRegistry(new ProtocolRegistry(ProtocolType.STATUS));

    InetSocketAddress socketAddress =
        createAddress(getServerData().getHostname(), getServerData().getPort());

    CountDownLatch latch = new CountDownLatch(1);
    socketServer = new SocketServer(this, latch);
    socketServer.bind(socketAddress);

    try {
      latch.await();
    } catch (InterruptedException e) {
      shutdown();
      return;
    }

    serverState = ServerState.RUNNING;
    LOGGER.info("Socket server has been successfully started.");
  }

  @Override
  public ServerState getServerState() {
    return serverState;
  }

  @Override
  public void setServerState(ServerState serverState) {
    this.serverState = serverState;
  }

  @Override
  public synchronized void heartbeat() {
    schedulerManager.runTask(playerSessionRegistry::tick);
    dimensionManager.tick();
    schedulerManager.heartbeat();
  }

  @Override
  public synchronized void shutdown() {
    LOGGER.info("Server shutdown...");
    chatManager.broadcastMessage("%red%Server is now shutting down, you will be disconnected.");
    setServerState(ServerState.STOPPING);
    AnnotationManager.ANNOTATION_TASK_EXECUTOR.interrupt();
    initializationManager.shutdown();
    stopServer();
    LOGGER.info("Server successfully shutdown.");
  }

  @Override
  public synchronized void run(Runnable runnable) {
    runnable.run();
  }

  @Override
  public ServerThread getServerThread() {
    return serverThread;
  }

  @Override
  public ConcurrentManager getConcurrentManager() {
    return concurrentManager;
  }

  @Override
  public ServerData getServerData() {
    return serverData;
  }

  @Override
  public ProtocolContainer getProtocolContainer() {
    return protocolContainer;
  }

  @Override
  public void setServerData(ServerData serverData) {
    Server.serverData = serverData;
  }

  @Override
  public PlayerSessionContainer getPlayerSessionRegistry() {
    return playerSessionRegistry;
  }

  @Override
  public Bootstrap getServerManager() {
    return bootstrap;
  }

  @Override
  public SocketServer getSocketServer() {
    return socketServer;
  }

  @Override
  public SchedulerManager getSchedulerManager() {
    return schedulerManager;
  }

  @Override
  public PacketManager getPacketManager() {
    return packetManager;
  }

  @Override
  public ChatManager getChatManager() {
    return chatManager;
  }

  public ServerVersion getServerVersion() {
    return serverVersion;
  }

  public KeyPair getSecurityKey() {
    return securityKey;
  }

  public DimensionManager getDimensionManager() {
    return dimensionManager;
  }

  public DescriptorManager getDescriptorManager() {
    return descriptorManager;
  }
}
