package com.github.avalon.dimension.handler;

import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.player.IPlayer;

import java.util.Map;
import java.util.Queue;

public interface IChunkService {

  void tick();

  /** @return Returns the current queue of chunk that should be processed to the client. */
  Map<IPlayer, Queue<ChunkRequest>> getCurrentChunkQueue();

  /**
   * Sends all necessary chunk data to the client through {@link
   * com.github.avalon.packet.packet.play.PacketChunkData}.
   *
   * @since 1.1
   * @param player Client that will receive the data.
   */
  void loadToClient(IPlayer player, IChunk chunk);

  /**
   * Send the information packet about unload of the chunk the client.
   *
   * @since 1.1
   * @param player Client that will receive the packet.
   */
  void unloadFromClient(IPlayer player, IChunk chunk);
}
