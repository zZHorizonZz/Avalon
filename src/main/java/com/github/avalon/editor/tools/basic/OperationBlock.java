package com.github.avalon.editor.tools.basic;

import com.github.avalon.common.math.Vector3;
import com.github.avalon.data.Material;

public class OperationBlock {

  private Vector3 position;
  private Material material;

  public OperationBlock(Vector3 position, Material material) {
    this.position = position;
    this.material = material;
  }

  public boolean isEmpty() {
    return material == null || material.equals(Material.AIR);
  }

  public Vector3 getPosition() {
    return position;
  }

  public void setPosition(Vector3 position) {
    this.position = position;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }
}
