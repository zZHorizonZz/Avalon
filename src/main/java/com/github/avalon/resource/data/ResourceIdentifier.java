package com.github.avalon.resource.data;

/**
 * This class is based on <url>https://mojang.com/</url>. Resource location is in style {@code
 * "minecraft:[name]"} usually if this server is running in vanilla mode but it should work if
 * server is running in and supports forge mods or any other modifications.
 *
 * @author Horizon
 * @version 1.0
 */
public class ResourceIdentifier {

  public static final ResourceIdentifier DIMENSION_TYPE = new ResourceIdentifier("dimension_type");
  public static final ResourceIdentifier WORLD_GEN_BIOME = new ResourceIdentifier("worldgen/biome");

  public static final String DEFAULT_RESOURCE_KEY = "minecraft";

  private String key = DEFAULT_RESOURCE_KEY;
  private String name;

  public ResourceIdentifier(String key, String name) {
    this.key = key;
    this.name = name;
  }

  public ResourceIdentifier(String name) {
    this.name = name;
  }

  /**
   * Return the parsed resource location in style {@code [key]:[name]}.
   *
   * @since 1.0
   * @return Parsed resource location.
   */
  public String getLocation() {
    return key + ':' + name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    ResourceIdentifier that = (ResourceIdentifier) object;

    if (getKey() != null ? !getKey().equals(that.getKey()) : that.getKey() != null) return false;
    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getKey() != null ? getKey().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ResourceIdentifier: " + " Key: '" + key + '\'' + ", Name: '" + name + '\'';
  }
}
