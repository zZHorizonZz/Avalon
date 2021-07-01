package com.github.avalon.editor.tools.basic;

import com.github.avalon.chat.command.ChatOperator;
import com.github.avalon.common.math.Vector3;
import com.github.avalon.data.Material;
import com.github.avalon.dimension.dimension.Dimension;

public class FillOperation extends CornerOperation {

  private Material newMaterial;

  public FillOperation(ChatOperator inform, Dimension dimension) {
    super(inform, dimension);
  }

  @Override
  public void collectBlocks(Vector3 cornerA, Vector3 cornerB) {
    for (int x = cornerA.getXAsInteger(); x <= cornerB.getXAsInteger(); x++) {
      for (int y = cornerA.getYAsInteger(); y <= cornerB.getYAsInteger(); y++) {
        for (int z = cornerA.getZAsInteger(); z <= cornerB.getZAsInteger(); z++) {
          Material oldBlockMaterial = getDimension().getBlockAt(x, y, z).getMaterial();
          if (oldBlockMaterial == newMaterial) {
            continue;
          }
          insert(new Vector3(x, y, z), newMaterial);
        }
      }
    }
  }

  public Material getNewMaterial() {
    return newMaterial;
  }

  public void setNewMaterial(Material newMaterial) {
    this.newMaterial = newMaterial;
  }
}
