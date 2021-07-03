package com.github.avalon.block.block;

import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.chunk.IChunkSection;

/**
 * Block represents a data of block that are saved in {@link
 * com.github.avalon.dimension.chunk.ChunkSection}. This block does not contain any data because it
 * returns data from their origin.
 *
 * @version 1.1
 */
public abstract class Block {

  private final Material material;
  private final Transform transform;

  private int state;

  protected Block(Transform transform) {
    this.transform = transform;

    material = transform.getChunk().getProvider().getMaterial(transform);
    state = transform.getChunk().getProvider().getState(transform);
  }

  public Transform getTransform() {
    return transform;
  }

  public IChunk getChunk() {
    return transform.getChunk();
  }

  /**
   * Because we can have multiple instances of same block and change materials in them we should not
   * store them in block instance. Instead we are storing them in chunk section and them retrieving
   * them with this method.
   *
   * @return Material of block. If material of block does not exist in {@link IChunkSection} then
   *     AIR is returned.
   */
  public Material getMaterial() {
    return material;
  }

  /**
   * Sets the material of current block at it's coordinates in {@link IChunk} respectively in {@link
   * IChunkSection}. And returns the new instance of block with new {@link Material}.
   *
   * @param material Material of block.
   */
  public Block setMaterial(Material material) {
    getChunk().getProvider().placeBlockAsSystem(transform, material);
    return material.createBlock(transform);
  }

  /** @return Returns the current state of block. */
  public int getState() {
    return state;
  }

  /**
   * Sets the new state of block. If new state is not valid then error is thrown.
   *
   * @param state New state of block.
   */
  public void setState(int state) {
    assert state > material.getValidStates()
        : "Invalid state for block " + getClass().getSimpleName() + '!';

    this.state = state;
    getChunk().getProvider().setStateAsSystem(transform, state);
  }
}
