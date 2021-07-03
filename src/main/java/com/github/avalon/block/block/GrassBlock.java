package com.github.avalon.block.block;

import com.github.avalon.data.Transform;

public class GrassBlock extends Block {

  private boolean snow;

  public GrassBlock(Transform transform) {
    super(transform);
  }

  public boolean hasSnow() {
    return snow;
  }

  public void setSnow(boolean snow) {
    this.snow = snow;
    setState(snow ? 0 : 1);
  }
}
