package com.github.avalon.language;

import java.util.Arrays;
import java.util.Optional;

public enum Language {
  EN_US("en_us"),
  EN_GB("en_gb"),
  UNKNOWN(null);

  private final String name;

  Language(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Language getLanguage(String name) {
    Optional<Language> result =
        Arrays.stream(Language.values())
            .filter(language -> language.getName().equalsIgnoreCase(name))
            .findFirst();

    return result.orElse(UNKNOWN);
  }
}
