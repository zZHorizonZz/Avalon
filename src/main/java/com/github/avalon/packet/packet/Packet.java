package com.github.avalon.packet.packet;

import com.flowpowered.network.AsyncableMessage;
import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.github.avalon.common.data.Array;
import com.github.avalon.common.data.DataType;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.network.PacketBuffer;
import com.github.avalon.packet.schema.ArrayScheme;
import com.github.avalon.packet.schema.EnumScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class Packet<P extends Message>
    implements Codec<Packet<?>>, MessageHandler<PlayerConnection, P>, AsyncableMessage {

  public static final DefaultLogger LOGGER = new DefaultLogger(Packet.class);

  @Override
  public Packet<?> decode(ByteBuf buffer) throws IOException {
    PacketBuffer packetBuffer = (PacketBuffer) buffer;
    for (FunctionScheme<?> scheme : getStrategy().getSchemes()) {
      try {
        if (scheme instanceof ArrayScheme) {
          ArrayScheme<?> arrayScheme = (ArrayScheme<?>) scheme;
          Array<?> array = packetBuffer.readArray(new Array<>(arrayScheme.getArrayType()));
          scheme.set(Objects.requireNonNull(array.get()));
        } else if (scheme instanceof EnumScheme) {
          EnumScheme<?> enumScheme = (EnumScheme<?>) scheme;
          Class<? extends Enum<?>> enumClass = enumScheme.getClazz();
          scheme.set(enumClass.getEnumConstants()[packetBuffer.readVarInt()]);
        } else {
          scheme.set(scheme.getType().read(packetBuffer));
        }
      } catch (RuntimeException exception) {
        LOGGER.error(
            "When decoding packet %s an error occurred on read of value with type %s and value %s",
            exception, getClass().getSimpleName(), scheme.getType(), scheme.get());
      }
    }
    return this;
  }

  @Override
  public ByteBuf encode(ByteBuf buffer, Packet<?> packet) throws IOException {
    PacketBuffer packetBuffer = (PacketBuffer) buffer;
    for (FunctionScheme<?> scheme : packet.getStrategy().getSchemes()) {
      try {
        if (scheme instanceof ArrayScheme) {
          ArrayScheme<?> arrayScheme = (ArrayScheme<?>) scheme;
          writeArray(arrayScheme.getArrayType(), arrayScheme.getFunction().get(), packetBuffer);
        } else if (scheme instanceof EnumScheme) {
          EnumScheme<?> enumScheme = (EnumScheme<?>) scheme;
          packetBuffer.writeVarInt(enumScheme.get().ordinal());
        } else {
          scheme.getType().write(packetBuffer, scheme.get());
        }
      } catch (RuntimeException exception) {
        LOGGER.error(
            "When encoding packet %s an error occurred on wrote of value with type %s and value %s",
            exception, getClass().getSimpleName(), scheme.getType(), scheme.get());
      }
    }
    return buffer;
  }

  private void writeArray(DataType<?> type, List set, PacketBuffer buffer) {
    Array<?> array = new Array<>(type);
    array.set(set);
    buffer.writeArray(array);
  }

  public abstract PacketStrategy getStrategy();
}
