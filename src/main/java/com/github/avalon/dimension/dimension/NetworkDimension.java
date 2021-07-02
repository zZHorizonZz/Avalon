package com.github.avalon.dimension.dimension;

import com.github.avalon.block.Block;
import com.github.avalon.character.character.Character;
import com.github.avalon.character.character.CharacterLiving;
import com.github.avalon.common.system.TripleMap;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.DimensionManager;
import com.github.avalon.dimension.biome.BiomeContainer;
import com.github.avalon.dimension.chunk.ChunkBatch;
import com.github.avalon.dimension.chunk.ChunkService;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.chunk.IChunkBatch;
import com.github.avalon.nbt.tag.Tag;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.nbt.tag.TagInteger;
import com.github.avalon.nbt.tag.TagString;
import com.github.avalon.player.IPlayer;
import com.github.avalon.resource.data.ResourceIdentifier;
import com.google.common.hash.Hashing;
import io.netty.util.internal.StringUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NetworkDimension implements Dimension {

  private final DimensionManager dimensionManager;

  private final long seed;
  private final TerrainType terrainType;
  private final DimensionType dimensionType;
  private final int dimensionIdentifier;

  private final ChunkService chunkService;

  private String dimensionName;
  private DimensionData dimensionData;
  private Difficulty difficulty;
  private boolean difficultyLocked;

  private final Map<Integer, IPlayer> players;
  private final TripleMap<Integer, Integer, IChunkBatch> chunkBatches;

  public NetworkDimension(
      DimensionManager dimensionManager,
      int dimensionIdentifier,
      String dimensionName,
      long seed,
      DimensionType dimensionType) {
    this.seed = seed;
    this.dimensionManager = dimensionManager;
    this.dimensionIdentifier = dimensionIdentifier;

    this.dimensionName = dimensionName;
    this.dimensionType = dimensionType;

    chunkService = new ChunkService(this);
    dimensionData = new DimensionData(this);
    difficulty = Difficulty.NORMAL;
    difficultyLocked = true;

    terrainType = TerrainType.FLAT;

    players = new HashMap<>();
    chunkBatches = new TripleMap<>();
  }

  @Override
  public void load() {
    assert !StringUtil.isNullOrEmpty(dimensionName) : "Name of the world can not be null or blank.";

    DimensionManager.LOGGER.info("Starting load of %s...", dimensionName);
    DimensionManager.LOGGER.info("Preparing spawn chunk batch...");
    loadChunkBatch(0, 0);
    DimensionManager.LOGGER.info(
        "Dimension %s has been successfully loaded. And chunk batch has been created.",
        dimensionName);
  }

  @Override
  public void unload() {}

  @Override
  public void tick() {
    if (chunkService != null) {
      chunkService.tick();
    }
  }

  @Override
  public IChunkBatch loadChunkBatch(int xCoordinate, int zCoordinate) {
    int batchSize = ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE;
    int xMin = -Math.floorDiv(batchSize, 2);
    int zMin = -Math.floorDiv(batchSize, 2);

    IChunkBatch chunkBatch = new ChunkBatch(this, xCoordinate, zCoordinate);

    for (int x = xMin; x <= batchSize - Math.floorDiv(batchSize, 2); x++) {
      for (int z = zMin; z <= batchSize - Math.floorDiv(batchSize, 2); z++) {
        chunkBatch.createChunk(x, z);
      }
    }

    chunkBatches.put(xCoordinate, zCoordinate, chunkBatch);
    return chunkBatch;
  }

  @Nullable
  @Override
  public IChunkBatch getBatchAt(int x, int z) {
    return chunkBatches.get(x, z);
  }

  @Override
  public IChunk getChunkAt(int x, int z) {
    int batchX = x / ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE;
    int batchZ = z / ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE;
    return Objects.requireNonNull(getBatchAt(batchX, batchZ))
        .getChunk(x % ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE, z % ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE);
  }

  @Override
  public Block getBlockAt(int x, int y, int z) {
    return new Block(new Transform(this, x, y, z));
  }

  @Override
  public String getDimensionName() {
    return dimensionName;
  }

  @Override
  public void setDimensionName(String dimensionName) {
    this.dimensionName = dimensionName;
  }

  @Override
  public int getIdentifier() {
    return dimensionIdentifier;
  }

  @Override
  public ResourceIdentifier getResourceIdentifier() {
    return new ResourceIdentifier(getDimensionName());
  }

  @Override
  public DimensionData getDimensionData() {
    return dimensionData;
  }

  @Override
  public void setDimensionData(DimensionData dimensionData) {
    this.dimensionData = dimensionData;
  }

  @Override
  public DimensionManager getDimensionManager() {
    return dimensionManager;
  }

  @Override
  public BiomeContainer getBiomeRegistry() {
    return null;
  }

  @Override
  public ChunkService getChunkService() {
    return chunkService;
  }

  @Override
  public DimensionType getType() {
    return dimensionType;
  }

  @Override
  public TerrainType getTerrainType() {
    return terrainType;
  }

  @Override
  public Difficulty getDifficulty() {
    return difficulty;
  }

  @Override
  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  @Override
  public boolean isDifficultyLocked() {
    return difficultyLocked;
  }

  @Override
  public void setDifficultyLocked(boolean locked) {
    difficultyLocked = locked;
  }

  @Override
  public void spawnCharacter(Character character, Transform transform) {}

  @Override
  public void spawnControllableCharacter(
      CharacterLiving character, IPlayer controller, Transform transform) {
    DimensionManager.LOGGER.info(
        "Spawning controllable character with id %s ...", character.getIdentifier());
    if (!controller.getConnection().isActive()) {
      return;
    }

    if (!character.getController().equals(controller)) {
      // TODO : Possible switch between controllers?
      character.setController(controller);
    }

    if (players.containsValue(controller)) {
      return; // TODO : Possible switch to new character?
    }

    character.spawn(controller);
    controller.setControllingCharacter(character);

    if (!players.isEmpty()) {
      players.forEach((id, player) -> character.spawn(player));
    }
    players.put(character.getIdentifier(), controller);
    DimensionManager.LOGGER.info(
        "Controllable character has been spawned. Controlling player is %s", character.getName());
  }

  @Override
  public Map<Integer, IPlayer> getPlayers() {
    return players;
  }

  @Override
  public TripleMap<Integer, Integer, IChunkBatch> getChunkBatches() {
    return chunkBatches;
  }

  @Override
  public long getSeed() {
    return seed;
  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public Long getHashedSeed() {
    return Hashing.sha256().hashLong(seed).asLong();
  }

  @Override
  public Tag serialize(Object object) {
    NetworkDimension dimension = (NetworkDimension) object;

    TagCompound mainParent = new TagCompound("");

    mainParent.add(new TagString("name", dimension.getResourceIdentifier().getLocation()));
    mainParent.add(new TagInteger("id", dimension.getIdentifier()));

    TagCompound elementParent = (TagCompound) dimension.getDimensionData().serialize(dimensionData);
    elementParent.setName("element");
    mainParent.add(elementParent);

    return mainParent;
  }
}
