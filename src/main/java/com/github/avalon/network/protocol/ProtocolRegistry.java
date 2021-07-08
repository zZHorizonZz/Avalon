package com.github.avalon.network.protocol;

import com.flowpowered.network.AsyncableMessage;
import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.exception.IllegalOpcodeException;
import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.service.CodecLookupService;
import com.flowpowered.network.util.ByteBufUtils;
import com.github.avalon.annotation.AnnotationModule;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.network.handler.NetworkHandlerService;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.player.PlayerConnection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import javassist.NotFoundException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ProtocolRegistry is class that stores and provides the registered {@link Packet} classes for
 * handling of network traffic events.
 *
 * @version 1.0
 */
public class ProtocolRegistry extends AbstractProtocol {

  public static final DefaultLogger LOGGER = new DefaultLogger(ProtocolRegistry.class);

  private final ProtocolType protocolType;

  private final CodecLookupService inbound;
  private final CodecLookupService outbound;
  private final NetworkHandlerService handlers;

  public ProtocolRegistry(ProtocolType protocolType) {
    super(protocolType.name());
    this.protocolType = protocolType;

    inbound = new CodecLookupService(protocolType.getHighestOperationCode() + 1);
    outbound = new CodecLookupService(protocolType.getHighestOperationCode() + 1);
    handlers = new NetworkHandlerService();

    registerPackets();
  }

  /**
   * Register {@link Packet} with specified {@link ProtocolType}. If we want to register packets
   * with this method, we should annotated these packet classes with {@link PacketRegister}
   * annotation. Otherwise, we can manually register them.
   *
   * @since 1.0
   */
  private void registerPackets() {
    Set<Class<?>> classes = AnnotationModule.scanForClassesByAnnotation(PacketRegister.class);
    classes =
        classes.stream()
            .filter(
                aClass -> {
                  if (Packet.class.isAssignableFrom(aClass)) {
                    return registerPacket((Class<? extends Packet>) aClass);
                  }

                  return false;
                })
            .collect(Collectors.toSet());

    LOGGER.info(
        "Packet registry for %s protocol has been created. Found %s packets.",
        protocolType.name(), classes.size());
  }

  /**
   * Register {@link Packet} class. Packet class should be annotated with {@link PacketRegister}.
   * Otherwise this method will throw exception.
   *
   * @param packet Class that will be registered.
   */
  private boolean registerPacket(Class<? extends Packet> packet) {
    PacketRegister register = packet.getDeclaredAnnotation(PacketRegister.class);
    if (register == null) {
      LOGGER.error(
          "Annotation not found when registering %s.",
          new NotFoundException("PacketRegister annotation was not found."),
          packet.getSimpleName());
      return false;
    }

    if (register.protocolType() != protocolType) {
      return false;
    }

    if (register.direction().equals(PacketRegister.Direction.SERVER)) {
      registerInbound(register.operationCode(), packet);
    } else {
      registerOutbound(register.operationCode(), packet);
    }

    return true;
  }

  protected <P extends AsyncableMessage & Codec<P> & MessageHandler<PlayerConnection, P>>
      void registerInbound(int operationCode, Class<P> packet) {
    try {
      inbound.bind(packet, packet, operationCode);
      handlers.bind(packet, packet);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException exception) {
      LOGGER.error(
          "Error occurred when registering %s inbound packet %s.",
          exception, protocolType.name(), operationCode);
    }
  }

  protected <P extends AsyncableMessage & Codec<P> & MessageHandler<PlayerConnection, P>>
      void registerInboundWithHandler(int operationCode, Class<P> packet, P handler) {
    try {
      inbound.bind(packet, packet, operationCode);
      handlers.bind(packet, handler);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException exception) {
      LOGGER.error(
          "Error occurred when registering %s inbound packet %s.",
          exception, protocolType.name(), operationCode);
    }
  }

  protected <P extends AsyncableMessage & Codec<P> & MessageHandler<PlayerConnection, P>>
      void registerOutbound(int operationCode, Class<P> packet) {
    try {
      outbound.bind(packet, packet, operationCode);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException exception) {
      LOGGER.error(
          "Error occurred when registering %s outbound packet %s.",
          exception, protocolType.name(), operationCode);
    }
  }

  @Nullable
  @Override
  public <M extends Message> MessageHandler<?, M> getMessageHandle(Class<M> aClass) {
    MessageHandler<?, M> handler = handlers.find(aClass);
    if (handler == null) {
      LOGGER.error(
          "Handler for %s was not found in %s.",
          new NotFoundException("Handler not found!"), aClass.getSimpleName(), getName());
    }

    return handler;
  }

  @Nullable
  @Deprecated
  @Override
  public Codec<?> readHeader(ByteBuf buffer) {
    int length = -1;
    int operationalCode = -1;
    try {
      length = ByteBufUtils.readVarInt(buffer);
      buffer.markReaderIndex();
      operationalCode = ByteBufUtils.readVarInt(buffer);
      return inbound.find(operationalCode);
    } catch (IOException exception) {
      LOGGER.error(
          "An error has occurred when reading the packet. With operation code %s and length of %s.",
          exception, operationalCode, length);
      return null;
    } catch (IllegalOpcodeException exception) {
      buffer.resetReaderIndex();
      LOGGER.error(
          "Any packet with %s operational code was not found. (Length %s)",
          exception, operationalCode, length);
      return null;
    }
  }

  @Override
  public <M extends Message> Codec.CodecRegistration getCodecRegistration(Class<M> clazz) {
    Codec.CodecRegistration reg = outbound.find(clazz);
    if (reg == null) {
      System.out.println("No codec to write: " + clazz.getSimpleName() + " in " + getName());
    }
    return reg;
  }

  @Override
  @Deprecated
  public ByteBuf writeHeader(ByteBuf out, Codec.CodecRegistration codec, ByteBuf data) {
    ByteBuf opcodeBuffer = Unpooled.buffer(5);
    ByteBufUtils.writeVarInt(opcodeBuffer, codec.getOpcode());
    ByteBufUtils.writeVarInt(out, opcodeBuffer.readableBytes() + data.readableBytes());
    opcodeBuffer.release();
    ByteBufUtils.writeVarInt(out, codec.getOpcode());
    return out;
  }

  public Codec<?> newReadHeader(ByteBuf in) throws IOException, IllegalOpcodeException {
    int opcode = ByteBufUtils.readVarInt(in);
    return inbound.find(opcode);
  }

  public ProtocolType getProtocolType() {
    return protocolType;
  }

  public CodecLookupService getInbound() {
    return inbound;
  }

  public CodecLookupService getOutbound() {
    return outbound;
  }

  public NetworkHandlerService getHandlers() {
    return handlers;
  }
}
