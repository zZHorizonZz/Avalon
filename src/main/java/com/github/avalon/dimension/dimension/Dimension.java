package com.github.avalon.dimension.dimension;

import com.github.avalon.block.block.Block;
import com.github.avalon.character.character.CharacterLiving;
import com.github.avalon.common.system.TripleMap;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.DimensionManager;
import com.github.avalon.dimension.biome.Biome;
import com.github.avalon.dimension.biome.BiomeContainer;
import com.github.avalon.dimension.chunk.ChunkService;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.chunk.IChunkBatch;
import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.player.IPlayer;
import com.github.avalon.resource.data.ResourceIdentifier;

import java.util.Collection;

/**
 * This interface provides an methods for creation and usage of dimension (worlds).
 *
 * @author Horizon
 */
public interface Dimension extends NamedBinarySerializer {

  /** Start loading of the world. */
  void load();

  /** Start unload of the world. */
  void unload();

  /** Is called every tick by server main thread tick. */
  void tick();

  /**
   * If {@link IChunkBatch} already exists it will be loaded otherwise new chunk batch will be
   * created.
   *
   * @param x X position
   * @param z Z position
   */
  IChunkBatch loadChunkBatch(int x, int z);

  IChunkBatch getBatchAt(int x, int z);

  IChunk getChunkAt(int x, int z);

  Block getBlockAt(int x, int y, int z);

  /** @return Name of the dimension. */
  String getDimensionName();

  /**
   * Sets the name of the dimension.
   *
   * @param dimensionName Name of the dimension.
   */
  void setDimensionName(String dimensionName);

  /** @return Returns the identifier of the dimension. */
  int getIdentifier();

  ResourceIdentifier getResourceIdentifier();

  /** @return Returns the dimension data. */
  DimensionData getDimensionData();

  /**
   * Sets the dimension data.
   *
   * @param dimensionData DimensionData
   */
  void setDimensionData(DimensionData dimensionData);

  /** @return Manager of the dimension. */
  DimensionManager getDimensionManager();

  /** @return Container of the {@link Biome} */
  BiomeContainer getBiomeRegistry();

  /** @return Returns the {@link ChunkService} that handles sending of chunks to client. */
  ChunkService getChunkService();

  /**
   * Returns the type of current dimension.
   *
   * @return Type of dimension.
   */
  DimensionType getType();

  /** @return Type of terrain. */
  TerrainType getTerrainType();

  /** @return Difficulty of the current dimension. */
  Difficulty getDifficulty();

  /**
   * Sets the current dimension difficulty.
   *
   * @param difficulty Difficulty that will be used as the world difficulty.
   */
  void setDifficulty(Difficulty difficulty);

  /** @return true if dimension difficulty is locked. Usually difficulty is locked by default. */
  boolean isDifficultyLocked();

  /**
   * Sets whether or not should be dimension difficulty locked.
   *
   * @param locked Lock or unlock the difficulty.
   */
  void setDifficultyLocked(boolean locked);

  /**
   * Spawns the character on the specified location in current dimension.
   *
   * @param character Character object that will be spawned.
   * @param transform Location of character to spawn.
   */
  void spawnCharacter(
      com.github.avalon.character.character.Character character, Transform transform);

  /**
   * Spawns the character on the specified location in current dimension. Control of the character
   * will be given to the player. This method should be called after the {@link IPlayer} joins the
   * game.
   *
   * @param character Character object that will be spawned.
   * @param controller {@link IPlayer} that will control the character.
   * @param transform Location of character to spawn.
   */
  void spawnControllableCharacter(
      CharacterLiving character, IPlayer controller, Transform transform);

  /** @return {@link Collection} of {@link IPlayer}s in current dimension. */
  Collection<IPlayer> getPlayers();

  /** @return World {@link IChunkBatch}s {@link TripleMap}. */
  TripleMap<Integer, Integer, IChunkBatch> getChunkBatches();

  /** @return Seed of the world. */
  long getSeed();

  /** @return Generated 256SHA1 hash code from the world seed. */
  Long getHashedSeed();
}
