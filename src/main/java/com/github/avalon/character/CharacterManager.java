package com.github.avalon.character;

import com.github.avalon.dimension.DimensionManager;
import com.github.avalon.manager.DefaultManager;

public class CharacterManager extends DefaultManager<DimensionManager> {

  public CharacterManager(String managerName, DimensionManager host) {
    super(managerName, host);
  }
}
