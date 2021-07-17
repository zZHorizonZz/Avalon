package com.github.avalon.player.handler;

import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.player.IPlayer;

public class PlayerChunkProvider {

  private final IPlayer player;

  public PlayerChunkProvider(IPlayer player) {
    this.player = player;
  }

  public void handleChunk() {
    IChunk currentChunk = player.getLocation().getChunk();
    Dimension dimension = player.getDimension();

    int maxRadius = 7;

    for (int r = 0; r <= maxRadius; r++) {
      for (int x = -r; x <= r; x++) {
        for (int z = -r; z <= r; z++) {
          if (x != -r && z != -r && x != r && z != r) {
            continue;
          }

          int xPosition = currentChunk.getX() + x;
          int zPosition = currentChunk.getZ() + z;

          IChunk chunk = dimension.getChunkAt(xPosition, zPosition);

          if (!player.getChunkView().contains(chunk.getPosition())) {
            player.getDimension().getChunkService().loadToClient(player, chunk);
            player.getChunkView().add(chunk.getPosition());
          }
        }
      }
    }
    /*int x = 0;
    int z = 0;
    int dx = 0;
    int dz = -1;

    int t = Math.max(7, 7);
    int maxI = t * t;

    for (int i = 0; i < maxI; i++) {
      if ((-7 / 2 <= x)
          && (x <= 7 / 2)
          && (-7 / 2 <= z)
          && (z <= z / 2)) {
        int xPosition = currentChunk.getX() + x;
        int zPosition = currentChunk.getZ() + z;

        IChunk chunk = dimension.getChunkAt(xPosition, zPosition);

        if (!player.getChunkView().contains(chunk.getPosition())) {
          player.getDimension().getChunkService().loadToClient(player, chunk);
          player.getChunkView().add(chunk.getPosition());
        }
      }

      if ((x == z) || ((x < 0) && (x == -z)) || ((x > 0) && (x == 1 - z))) {
        t = dx;
        dx = -dz;
        dz = t;
      }
      x += dx;
      z += dz;
    }*/
  }

  public IPlayer getPlayer() {
    return player;
  }
}
