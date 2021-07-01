package com.github.avalon.dimension.handler;

import com.github.avalon.dimension.chunk.Chunk;
import com.github.avalon.dimension.chunk.ChunkStatus;
import com.github.avalon.packet.packet.play.PacketChunkData;
import com.github.avalon.player.IPlayer;

import java.util.concurrent.Callable;

public class ChunkRequest implements Callable<Boolean> {

  private final Chunk chunk;
  private final IPlayer player;

  private final ChunkStatus status;

  public ChunkRequest(Chunk chunk, IPlayer player) {
    this.player = player;
    this.chunk = chunk;

    status = chunk.getStatus();
  }

  @Override
  public Boolean call() {
    if (!player.getConnection().isActive()) {
      return false;
    }

    System.out.println("Sending chunk: " + chunk.getX() + " , " + chunk.getZ());

    PacketChunkData data = new PacketChunkData(chunk);
    /*PacketUpdateLight light =
        new PacketUpdateLight(position.getXAsInteger(), position.getZAsInteger(), true);*/
    player.sendPacket(data);
    // player.sendPacket(light);

    return true;
  }

  public IPlayer getPlayer() {
    return player;
  }

  public ChunkStatus getStatus() {
    return status;
  }
}
