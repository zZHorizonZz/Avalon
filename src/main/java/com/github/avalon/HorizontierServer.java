package com.github.avalon;

import com.github.avalon.server.Bootstrap;
import com.github.avalon.server.Initializer;

public final class HorizontierServer {

  public static void main(String[] arguments) {
    Initializer serverRunner = new Bootstrap();
    serverRunner.initialize();
  }
}
