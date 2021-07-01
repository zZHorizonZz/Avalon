package com.github.avalon.dimension;

import com.github.avalon.annotation.annotation.Manager;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.dimension.biome.BiomeContainer;
import com.github.avalon.dimension.biome.BiomeEffect;
import com.github.avalon.dimension.biome.NetworkBiome;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.dimension.dimension.DimensionContainer;
import com.github.avalon.dimension.dimension.DimensionType;
import com.github.avalon.dimension.dimension.NetworkDimension;
import com.github.avalon.dimension.handler.ChunkService;
import com.github.avalon.manager.ServerManager;
import com.github.avalon.server.IServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Manager(name = "Dimension Manager", asynchronous = false)
public class DimensionManager extends ServerManager {

  public static final DefaultLogger LOGGER = new DefaultLogger(DimensionManager.class);

  private Dimension mainDimension;

  private final DimensionContainer dimensionRegistry;
  private final BiomeContainer biomeRegistry;

  private ChunkService chunkHandler;

  public DimensionManager(IServer host) {
    super(host);

    biomeRegistry = new BiomeContainer();
    dimensionRegistry = new DimensionContainer(host);

    registerBiomes();
    loadDimension();
  }

  @Override
  public void enable() {
    super.enable();

    chunkHandler = new ChunkService(this);
  }

  public void registerBiomes() {
    NetworkBiome biome = new NetworkBiome("plains", 0);
    biome.getBiomeData().setBiomeEffect(new BiomeEffect());

    biomeRegistry.registerBiome(0, biome);
  }

  public void loadDimension() {
    Dimension dimension =
        new NetworkDimension(this, 0, "overworld", new Random().nextLong(), DimensionType.NORMAL);

    dimension.load();
    dimensionRegistry.addDimension(dimension.getDimensionName(), dimension);
    mainDimension = dimension;
  }

  public List<Dimension> getDimensions() {
    return new ArrayList<>(dimensionRegistry.getRegistry().values());
  }

  public DimensionContainer getDimensionRegistry() {
    return dimensionRegistry;
  }

  public Dimension getMainDimension() {
    return mainDimension;
  }

  public void setMainDimension(Dimension mainDimension) {
    this.mainDimension = mainDimension;
  }

  public BiomeContainer getBiomeRegistry() {
    return biomeRegistry;
  }

  public ChunkService getChunkHandler() {
    return chunkHandler;
  }
}
