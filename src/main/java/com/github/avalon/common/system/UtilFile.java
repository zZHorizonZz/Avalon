package com.github.avalon.common.system;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UtilFile {

  public File loadResource(String name) {
    File finalFile = new File(name);

    try {
      InputStream fileStream = getClass().getClassLoader().getResourceAsStream(name);

      assert fileStream != null;
      byte[] buffer = fileStream.readAllBytes();

      Files.write(buffer, finalFile);
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return finalFile;
  }
}
