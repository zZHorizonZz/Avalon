package com.github.avalon.dimension.handler;

import com.github.avalon.dimension.DimensionManager;
import com.github.avalon.dimension.chunk.Chunk;
import com.github.avalon.player.IPlayer;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;

public class ChunkService implements IChunkService {

  private DimensionManager dimensionManager;

  private final Map<IPlayer, Queue<ChunkRequest>> chunkQueue;

  public ChunkService(DimensionManager dimensionManager) {
      chunkQueue = new ConcurrentHashMap<>();

    dimensionManager.getSchedulerManager().runRepeatingTask(this::tick, 40);
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

      ChunkRequest chunk = queue.poll();
      if (chunk.call()) {

      } else {

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
  public void loadToClient(IPlayer player, Chunk chunk) {
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
  public void unloadFromClient(IPlayer player, Chunk chunk) {}
}
