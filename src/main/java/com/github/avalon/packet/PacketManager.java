package com.github.avalon.packet;

import com.github.avalon.annotation.annotation.Manager;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.manager.ServerManager;
import com.github.avalon.packet.annotation.PacketHandler;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.server.IServer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Manager(name = "Packet Manager", asynchronous = true)
public class PacketManager extends ServerManager {

  public static final DefaultLogger LOGGER = new DefaultLogger(PacketManager.class);

  private final Map<PacketListener, Map<Class<? extends Packet<?>>, List<Method>>> packetListeners;

  public PacketManager(IServer host) {
    super(host);
    packetListeners = new HashMap<>();
  }

  public void registerPacketListener(PacketListener listener) {
    LOGGER.info("Registering %s as Packet Listener.", listener.getClass().getSimpleName());
    Method[] methods = listener.getClass().getDeclaredMethods();

    for (Method method : methods) {
      if (Arrays.stream(method.getAnnotations())
          .anyMatch(
              annotation -> annotation.annotationType().isAssignableFrom(PacketHandler.class))) {
        registerPacketHandler(listener, method);
      }
    }

    LOGGER.info("%s was been successfully registered.", listener.getClass().getSimpleName());
  }

  public void registerPacketHandler(PacketListener listener, Method handler) {
    Objects.requireNonNull(handler, "Handler method that should be registered is null.");

    Annotation[] annotations = handler.getAnnotations();

    assert annotations.length >= 1
        : "If you want to register handler it should be annotated with some of the PacketHandlers.";

    for (Annotation annotation : annotations) {
      if (annotation.annotationType().isAssignableFrom(PacketHandler.class)) {
        assert handler.getParameterTypes().length <= 1
            : "Handler should have only one argument. Currently has "
                + handler.getParameterTypes().length;

        Optional<Class<?>> packetClass =
            Arrays.stream(handler.getParameterTypes())
                .filter(Packet.class::isAssignableFrom)
                .findAny();

        if (packetClass.isPresent()) {
          Map<Class<? extends Packet<?>>, List<Method>> packets = new HashMap<>();
          if (packetListeners.containsKey(listener)) {
            packets = packetListeners.get(listener);
          } else {
            packetListeners.put(listener, packets);
          }

          assert packets != null;

          List<Method> methods = new LinkedList<>();

          if (packets.containsKey((Class<? extends Packet<?>>) packetClass.get())) {
            methods = packets.get((Class<? extends Packet<?>>) packetClass.get());
          } else {
            packets.put((Class<? extends Packet<?>>) packetClass.get(), methods);
          }

          assert methods != null;
          methods.add(handler);
        }
      }
    }
  }

  public void handle(Packet<?> packet, PlayerConnection connection) {
    if (packetListeners.isEmpty()) {
      return;
    }

    for (Map.Entry<PacketListener, Map<Class<? extends Packet<?>>, List<Method>>> entry :
        packetListeners.entrySet()) {
      PacketListener listener = entry.getKey();
      Map<Class<? extends Packet<?>>, List<Method>> packetMethods = entry.getValue();

      List<Method> methods = packetMethods.getOrDefault(packet.getClass(), null);
      if (methods != null && !methods.isEmpty()) {
        methods.forEach(
            method -> {
              try {
                method.invoke(listener, packet, connection);
              } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
              }
            });
      }
    }
  }

  public Map<PacketListener, Map<Class<? extends Packet<?>>, List<Method>>> getPacketListeners() {
    return packetListeners;
  }
}
