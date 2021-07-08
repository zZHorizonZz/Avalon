package com.github.avalon.server;

import com.github.avalon.account.handler.LoginHandler;
import com.github.avalon.annotation.AnnotationModule;
import com.github.avalon.chat.ChatHandler;
import com.github.avalon.chat.ChatModule;
import com.github.avalon.common.system.UtilSecurity;
import com.github.avalon.concurrent.ConcurrentModule;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.debug.DebugModule;
import com.github.avalon.descriptor.DescriptorModule;
import com.github.avalon.dimension.DimensionModule;
import com.github.avalon.editor.EditModule;
import com.github.avalon.initialization.InitializationManager;
import com.github.avalon.item.ItemModule;
import com.github.avalon.network.PlayerSessionContainer;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.network.SocketServer;
import com.github.avalon.network.protocol.ProtocolContainer;
import com.github.avalon.network.protocol.ProtocolRegistry;
import com.github.avalon.packet.PacketModule;
import com.github.avalon.player.handler.BlockHandler;
import com.github.avalon.player.handler.MovementHandler;
import com.github.avalon.scheduler.SchedulerModule;
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

  private final ConcurrentModule concurrentModule;
  private PacketModule packetModule;
  private SchedulerModule schedulerModule;
  private DescriptorModule descriptorModule;
  private DimensionModule dimensionModule;
  private ChatModule chatModule;
  private ItemModule itemModule;
  private EditModule editModule;
  private DebugModule debugModule;

  private final PlayerSessionContainer playerSessionRegistry;

  public Server(Bootstrap bootstrap) {
    this.bootstrap = bootstrap;

    concurrentModule = new ConcurrentModule(this);
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

    packetModule = initializationManager.registerModule(new PacketModule(this));
    schedulerModule = initializationManager.registerModule(new SchedulerModule(this));
    descriptorModule = initializationManager.registerModule(new DescriptorModule(this));
    dimensionModule = initializationManager.registerModule(new DimensionModule(this));
    chatModule = initializationManager.registerModule(new ChatModule(this));
    itemModule = initializationManager.registerModule(new ItemModule(this));
    editModule = initializationManager.registerModule(new EditModule(this));
    debugModule = initializationManager.registerModule(new DebugModule(this));

    AnnotationModule.ANNOTATION_TASK_EXECUTOR.initialize(
        new DefaultThreadFactory("Annotation_Executor"));

    bindServer();

    if (serverThread != null) {
      serverThread.start();
    } else {
      shutdown();
    }

    packetModule.registerPacketListener(new MovementHandler());
    packetModule.registerPacketListener(new BlockHandler());
    packetModule.registerPacketListener(new ChatHandler());
    packetModule.registerPacketListener(new LoginHandler());

    chatModule.registerCommands(new StopCommand());

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
    schedulerModule.runTask(playerSessionRegistry::tick);
    dimensionModule.tick();
    schedulerModule.heartbeat();
  }

  @Override
  public synchronized void shutdown() {
    LOGGER.info("Server shutdown...");
    chatModule.broadcastMessage("%red%Server is now shutting down, you will be disconnected.");
    setServerState(ServerState.STOPPING);
    AnnotationModule.ANNOTATION_TASK_EXECUTOR.interrupt();
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
  public ConcurrentModule getConcurrentModule() {
    return concurrentModule;
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
  public Bootstrap getBootstrap() {
    return bootstrap;
  }

  @Override
  public SocketServer getSocketServer() {
    return socketServer;
  }

  @Override
  public SchedulerModule getSchedulerModule() {
    return schedulerModule;
  }

  @Override
  public PacketModule getPacketModule() {
    return packetModule;
  }

  @Override
  public ChatModule getChatModule() {
    return chatModule;
  }

  public ServerVersion getServerVersion() {
    return serverVersion;
  }

  public KeyPair getSecurityKey() {
    return securityKey;
  }

  public DimensionModule getDimensionModule() {
    return dimensionModule;
  }

  public DescriptorModule getDescriptorModule() {
    return descriptorModule;
  }
}
