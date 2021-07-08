package com.github.avalon.character;

import com.github.avalon.dimension.DimensionModule;
import com.github.avalon.module.DefaultModule;

public class CharacterModule extends DefaultModule<DimensionModule> {

  public CharacterModule(String managerName, DimensionModule host) {
    super(managerName, host);
  }
}
