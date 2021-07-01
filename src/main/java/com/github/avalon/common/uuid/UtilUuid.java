package com.github.avalon.common.uuid;

import java.util.UUID;
import java.util.regex.Pattern;

public class UtilUuid {

  private static final Pattern STRIPPED_UUID_PATTERN =
      Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
  private static final Pattern UUID_PATTERN =
      Pattern.compile("(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})");

  /**
   * Creates uuid from given string is is possible.
   *
   * @param uuid String uuid.
   * @return Creates the uuid.
   */
  public static UUID getUuidFromString(String uuid) {
    return UUID.fromString(STRIPPED_UUID_PATTERN.matcher(uuid).replaceAll("$1-$2-$3-$4-$5"));
  }
}
