package com.github.avalon.block;

import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.chunk.IChunkSection;

public class Block {

  private final Transform transform;

  public Block(Transform transform) {
    this.transform = transform;
  }

  public Transform getTransform() {
    return transform;
  }

  /**
   * Because we can have multiple instances of same block and change materials in them we should not
   * store them in block instance. Instead we are storing them in chunk section and them retrieving
   * them with this method.
   *
   * @return Material of block. If material of block does not exist in {@link
   *     IChunkSection} then AIR is returned.
   */
  public Material getMaterial() {
    return transform.getChunk().getProvider().getMaterial(transform);
  }

  /**
   * Sets the material of current block at it's coordinates in {@link IChunk} respectively in {@link
   * IChunkSection}.
   *
   * @param material Material of block.
   */
  public void setMaterial(Material material) {
    transform.getChunk().getProvider().placeBlockAsSystem(transform, material);
  }

  public IChunk getChunk() {
    return transform.getChunk();
  }
}
