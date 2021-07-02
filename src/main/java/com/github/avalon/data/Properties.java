package com.github.avalon.data;

/**
 * This class provides data about player.
 *
 * @version 1.0
 */
public class Properties {

  private String name;
  private String value;
  private String signature;

  public Properties() {}

  public Properties(String name, String value, String signature) {
    this.name = name;
    this.value = value;
    this.signature = signature;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
