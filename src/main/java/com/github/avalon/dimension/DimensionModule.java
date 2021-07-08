package com.github.avalon.dimension;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.dimension.biome.BiomeContainer;
import com.github.avalon.dimension.biome.BiomeEffect;
import com.github.avalon.dimension.biome.NetworkBiome;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.dimension.dimension.DimensionContainer;
import com.github.avalon.dimension.dimension.DimensionType;
import com.github.avalon.dimension.dimension.NetworkDimension;
import com.github.avalon.module.ServerModule;
import com.github.avalon.server.IServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Module(name = "Dimension Module", asynchronous = true)
public class DimensionModule extends ServerModule {

  public static final DefaultLogger LOGGER = new DefaultLogger(DimensionModule.class);

  private Dimension mainDimension;

  private final DimensionContainer dimensionRegistry;
  private final BiomeContainer biomeRegistry;

  public DimensionModule(IServer host) {
    super(host);

    biomeRegistry = new BiomeContainer();
    dimensionRegistry = new DimensionContainer(host);

    registerBiomes();
    loadDimension();
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

  public void tick() {
    runTaskAsynchronously(() -> getDimensions().forEach(Dimension::tick));
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
}
