package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.dimension.chunk.Chunk;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.ArrayScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Chunk data packet contains data about chunk his heightmaps, biomes, data (that contains blocks
 * that are already in chunk) and block entities that are entities that are considered as blocks for
 * example shulkerbox.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. X coordinate of chunk
 *   <li>2. Z coordinate of chunk
 *   <li>3. If chunk should be full.
 *   <li>4. Bit mask of chunk. Currently 0.
 *   <li>5. Height of chunk.
 *   <li>6. Biomes in current chunk every chunk contains 1024 biome cells that are 4*4*4 blocks.
 *   <li>7. Data of chunk blocks.
 *   <li>8. Block entities in chunk.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x20,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketChunkData extends Packet<PacketChunkData> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.INTEGER, this::getX, this::setX),
          new FunctionScheme<>(DataType.INTEGER, this::getZ, this::setZ),
          new FunctionScheme<>(DataType.BOOLEAN, this::isFullChunk, this::setFullChunk),
          new FunctionScheme<>(DataType.VARINT, this::getBitMask, this::setBitMask),
          new FunctionScheme<>(DataType.NBT_TAG, this::getHeightMap, this::setHeightMap),
          new ArrayScheme<>(DataType.VARINT, this::getBiomes, this::setBiomes),
          new FunctionScheme<>(DataType.BYTE_ARRAY, this::getData, this::setData),
          new ArrayScheme<>(DataType.NBT_TAG, this::getBlockEntities, this::setBlockEntities));

  private int x;
  private int z;
  private boolean fullChunk;
  private int bitMask;
  private TagCompound heightMap;
  private List<Integer> biomes;
  private byte[] data;
  private List<TagCompound> blockEntities;

  public PacketChunkData() {}

  public PacketChunkData(Chunk chunk) {
    x = chunk.getX();
    z = chunk.getZ();

    fullChunk = true;
    bitMask = 0;
    heightMap = (TagCompound) chunk.serialize(chunk);
    biomes = new ArrayList<>();
    for (int i = 1; i <= 1024; i++) {
      biomes.add(0);
    }
    data = new byte[0];
    blockEntities = new ArrayList<>();
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketChunkData packetChunkData) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }

  public boolean isFullChunk() {
    return fullChunk;
  }

  public void setFullChunk(boolean fullChunk) {
    this.fullChunk = fullChunk;
  }

  public int getBitMask() {
    return bitMask;
  }

  public void setBitMask(int bitMask) {
    this.bitMask = bitMask;
  }

  public TagCompound getHeightMap() {
    return heightMap;
  }

  public void setHeightMap(TagCompound heightMap) {
    this.heightMap = heightMap;
  }

  public List<Integer> getBiomes() {
    return biomes;
  }

  public void setBiomes(List<Integer> biomes) {
    this.biomes = biomes;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public List<TagCompound> getBlockEntities() {
    return blockEntities;
  }

  public void setBlockEntities(List<TagCompound> blockEntities) {
    this.blockEntities = blockEntities;
  }
}
