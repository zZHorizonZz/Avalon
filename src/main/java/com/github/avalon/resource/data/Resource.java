package com.github.avalon.resource.data;

import javassist.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Provides an reading of resource files from folder resources. To read this file method {@code
 * loadResource()} should be called. Then all content of the file will be saved into content string
 * or method {@code getResourceContent()} returns the string with content.
 *
 * @author Horizon
 * @version 1.0
 */
public class Resource {

  private String path;
  private String name;

  private String content;

  public Resource(String path, String name) {
    this.path = path;
    this.name = name;
  }

  public BufferedReader loadResource() throws NotFoundException {
    return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
  }

  public InputStream getInputStream() throws NotFoundException {
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    InputStream inputStream =
            classLoader.getResourceAsStream((path != null ? path + '/' : "") + name);

    if (inputStream == null) {
      throw new NotFoundException(
              "Resource with path " + path + " and name " + name + " was not found.");
    }

    return inputStream;
  }

  public String getResourceContent() {
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {
      bufferedReader = loadResource();
      String line = bufferedReader.readLine();

      while (line != null) {
        stringBuilder.append(line);
        stringBuilder.append(System.lineSeparator());
        line = bufferedReader.readLine();
      }
    } catch (IOException | NotFoundException exception) {
      exception.printStackTrace();
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }

    content = stringBuilder.toString();
    return content;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }
}
