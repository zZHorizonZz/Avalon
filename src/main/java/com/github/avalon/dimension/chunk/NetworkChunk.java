package com.github.avalon.dimension.chunk;

import com.github.avalon.common.math.Vector2;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkChunk implements Chunk {

  private final ChunkBatch chunkBatch;
  private final Vector2 position;

  private ChunkStatus status;
  private final Map<Integer, com.github.avalon.character.character.Character> characters;
  private final ChunkSectionProvider sectionProvider;

  public NetworkChunk(ChunkBatch chunkBatch, int x, int z) {
    this.chunkBatch = chunkBatch;
    position = new Vector2(x, z);
    characters = new ConcurrentHashMap<>();
    sectionProvider = new ChunkSectionProvider(this);

    status = ChunkStatus.UNLOADED;
  }

  @Override
  public ChunkBatch getChunkBatch() {
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
  public com.github.avalon.character.character.Character getCharacter(
      int characterIdentifier) {
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

    NetworkChunk that = (NetworkChunk) o;

    return getPosition().equals(that.getPosition());
  }

  @Override
  public int hashCode() {
    return getPosition().hashCode();
  }

  @Override
  public String toString() {
    return "NetworkChunk:"
        + " X: "
        + position.getX()
        + ", Z: "
        + position.getZ()
        + ", Status: "
        + status;
  }
}
