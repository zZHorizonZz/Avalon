package com.github.avalon.dimension.chunk;

import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.dimension.handler.ChunkRequest;
import com.github.avalon.dimension.handler.IChunkService;
import com.github.avalon.player.IPlayer;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;

public class ChunkService implements IChunkService {

  public static final int MAX_CHUNKS_PER_TICK = 2;

  private final Dimension dimension;
  private final Map<IPlayer, Queue<ChunkRequest>> chunkQueue;

  public ChunkService(Dimension dimension) {
    this.dimension = dimension;

    chunkQueue = new ConcurrentHashMap<>();
  }

  @Override
  public void tick() {
    if (chunkQueue.isEmpty()) {
      return;
    }

    Iterator<Map.Entry<IPlayer, Queue<ChunkRequest>>> chunkIterator =
        chunkQueue.entrySet().iterator();
    while (chunkIterator.hasNext()) {
      Map.Entry<IPlayer, Queue<ChunkRequest>> entry = chunkIterator.next();
      IPlayer player = entry.getKey();
      Queue<ChunkRequest> queue = entry.getValue();

      for (int i = 0; i < (Math.max(MAX_CHUNKS_PER_TICK, queue.size())); i++) {
        ChunkRequest chunk = queue.poll();
        if (chunk != null && chunk.call()) {

        } else {

        }
      }

      if (queue.isEmpty()) {
        chunkIterator.remove();
      }
    }
  }

  @Override
  public Map<IPlayer, Queue<ChunkRequest>> getCurrentChunkQueue() {
    return chunkQueue;
  }

  @Override
  public void loadToClient(IPlayer player, IChunk chunk) {
    if (chunkQueue.containsKey(player)) {
      Queue<ChunkRequest> queue = chunkQueue.get(player);
      ChunkRequest request = new ChunkRequest(chunk, player);
      queue.offer(request);
    } else {
      Queue<ChunkRequest> queue = new LinkedTransferQueue<>();
      ChunkRequest request = new ChunkRequest(chunk, player);
      queue.offer(request);
      chunkQueue.put(player, queue);
    }
  }

  @Override
  public void unloadFromClient(IPlayer player, IChunk chunk) {}

  public Dimension getDimension() {
    return dimension;
  }
}
