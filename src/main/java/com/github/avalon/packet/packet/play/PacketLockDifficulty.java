package com.github.avalon.packet.packet.play;

import com.github.avalon.network.PacketBuffer;
import com.github.avalon.packet.packet.LegacyPacket;
import com.github.avalon.player.PlayerConnection;

import java.io.IOException;

public class PacketLockDifficulty extends LegacyPacket<PacketLockDifficulty> {

  private boolean lock;

  public PacketLockDifficulty(boolean lock) {
    super(0x11);

    this.lock = lock;
  }

  public PacketLockDifficulty() {
    super(0x11);
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public PacketLockDifficulty decode(PacketBuffer byteBuf) throws IOException {
    return new PacketLockDifficulty(byteBuf.readBoolean());
  }

  @Override
  public PacketBuffer encode(PacketBuffer byteBuf, PacketLockDifficulty packetLockDifficulty)
      throws IOException {
    throw new UnsupportedOperationException("This operation is not supported");
  }

  @Override
  public void handle(PlayerConnection connection, PacketLockDifficulty packetLockDifficulty) {}

  public boolean isLock() {
    return lock;
  }
}
