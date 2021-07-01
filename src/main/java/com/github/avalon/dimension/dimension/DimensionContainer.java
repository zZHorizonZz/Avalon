package com.github.avalon.dimension.dimension;

import com.github.avalon.data.Container;
import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.nbt.tag.Tag;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.nbt.tag.TagList;
import com.github.avalon.nbt.tag.TagString;
import com.github.avalon.packet.packet.play.PacketJoinGame;
import com.github.avalon.resource.data.ResourceIdentifier;
import com.github.avalon.server.IServer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for storing the {@link Dimension}.
 *
 * @author Horizon
 * @version 1.0
 */
public class DimensionContainer extends Container<String, Dimension>
    implements NamedBinarySerializer {

  private final IServer server;

  public DimensionContainer(IServer server) {
    this.server = server;
  }

  @Override
  public Tag serialize(Object object) {
    PacketJoinGame packet = (PacketJoinGame) object;

    TagCompound mainParent = new TagCompound("");

    TagCompound dimensionTypeParent =
        new TagCompound(ResourceIdentifier.DIMENSION_TYPE.getLocation());
    mainParent.add(dimensionTypeParent);

    TagCompound worldGenerationParent =
        new TagCompound(ResourceIdentifier.WORLD_GEN_BIOME.getLocation());
    mainParent.add(worldGenerationParent);

    TagString dimensionType =
        new TagString("type", ResourceIdentifier.DIMENSION_TYPE.getLocation());
    TagString worldGenerationType =
        new TagString("type", ResourceIdentifier.WORLD_GEN_BIOME.getLocation());

    List<TagCompound> serializedDimensions =
            getRegistry().values().stream()
                .map(dimension -> (TagCompound) dimension.serialize(dimension))
                .collect(Collectors.toList());

        List<TagCompound> serializedBiomes =
            getDimensionByIdentifier(packet.getDimensionIdentifier())
                .getDimensionManager()
                .getBiomeRegistry()
                .getBiomes()
                .stream()
                .map(biome -> (TagCompound) biome.serialize(biome))
                .collect(Collectors.toList());

    TagList<TagCompound> dimensions =
        new TagList<>("value", TagCompound.class, serializedDimensions);
    TagList<TagCompound> biomes = new TagList<>("value", TagCompound.class, serializedBiomes);

    dimensionTypeParent.add(dimensionType);
    dimensionTypeParent.add(dimensions);

    worldGenerationParent.add(worldGenerationType);
    worldGenerationParent.add(biomes);

    return mainParent;
  }

  public void addDimension(String key, Dimension value) {
    add(key, value);
  }

  public void removeDimension(String key) {
    remove(key);
  }

  public Dimension getDimension(String key) {
    return get(key);
  }

  public Dimension getDimensionByIdentifier(ResourceIdentifier identifier) {
    return getRegistry().get(identifier.getName());
  }

  public boolean isDimension(String key) {
    return containsKey(key);
  }

  public boolean isDimension(Dimension value) {
    return containsValue(value);
  }

  public IServer getServer() {
    return server;
  }
}
