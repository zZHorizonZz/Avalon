package com.github.avalon.dimension.chunk;

import com.github.avalon.common.math.Vector2;
import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Chunk implements IChunk {

  private final IChunkBatch chunkBatch;
  private final Vector2 position;

  private ChunkStatus status;
  private final Map<Integer, com.github.avalon.character.character.Character> characters;
  private final ChunkSectionProvider sectionProvider;

  public Chunk(IChunkBatch chunkBatch, int x, int z) {
    this.chunkBatch = chunkBatch;
    position = new Vector2(x, z);
    characters = new ConcurrentHashMap<>();
    sectionProvider = new ChunkSectionProvider(this);

    status = ChunkStatus.UNLOADED;
    loadDebugLayer();
  }

  public void loadDebugLayer() {
    for (int x = 0; x <= 15; x++) {
      for (int z = 0; z <= 15; z++) {
        if (x != 0 && x != 15 && z != 0 && z != 15) {
          sectionProvider.placeBlockAsSystem(
              new Transform(
                  chunkBatch.getDimension(),
                  (getX() == 0 ? x : getX() * 16 + x),
                  32,
                  (getZ() == 0 ? z : getZ() * 16 + z)),
              Material.POLISHED_ANDESITE);
        } else {
          sectionProvider.placeBlockAsSystem(
              new Transform(
                  chunkBatch.getDimension(),
                  (getX() == 0 ? x : getX() * 16 + x),
                  32,
                  (getZ() == 0 ? z : getZ() * 16 + z)),
              Material.GRANITE);
        }
        /*sectionProvider.placeBlockAsSystem(
        new Transform(chunkBatch.getDimension(), (getX() == 0 ? 0 : getX() * 16) + x, 0, (getZ() == 0 ? 0 : getZ() * 16) + z), Material.STONE);*/
      }
    }
  }

  @Override
  public IChunkBatch getChunkBatch() {
    return chunkBatch;
  }

  @Override
  public Vector2 getPosition() {
    return position;
  }

  @Override
  public int getX() {
    return position.getXAsInteger();
  }

  @Override
  public int getZ() {
    return position.getZAsInteger();
  }

  @Override
  public ChunkStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(ChunkStatus chunkStatus) {
    status = chunkStatus;
  }

  @Override
  public Collection<com.github.avalon.character.character.Character> getCharacters() {
    return characters.values();
  }

  @Override
  public void characterEnterChunk(com.github.avalon.character.character.Character character) {
    characters.put(character.getIdentifier(), character);
  }

  @Override
  public void characterLeaveChunk(int characterIdentifier) {
    characters.remove(characterIdentifier);
  }

  @Override
  public com.github.avalon.character.character.Character getCharacter(int characterIdentifier) {
    return characters.get(characterIdentifier);
  }

  @Override
  public ChunkStatus loadChunk() {
    return null;
  }

  @Override
  public ChunkStatus unloadChunk(boolean force) {
    return null;
  }

  @Override
  public ChunkSectionProvider getProvider() {
    return sectionProvider;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Chunk that = (Chunk) o;

    return getPosition().equals(that.getPosition());
  }

  @Override
  public int hashCode() {
    return getPosition().hashCode();
  }

  @Override
  public String toString() {
    return "Chunk:" + " X: " + position.getX() + ", Z: " + position.getZ() + ", Status: " + status;
  }
}
