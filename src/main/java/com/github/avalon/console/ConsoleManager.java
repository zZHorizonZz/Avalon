package com.github.avalon.console;

import com.github.avalon.server.Bootstrap;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class ConsoleManager {

  public static final Logger LOGGER = Logger.getGlobal();

  private final Bootstrap bootstrap;

  public ConsoleManager(Bootstrap bootstrap) {
    this.bootstrap = bootstrap;
    ConsoleHandler handler = new ConsoleHandler();

    LOGGER.setUseParentHandlers(false);
    Formatter formatter = new LogFormatter();
    handler.setFormatter(formatter);

    LOGGER.addHandler(handler);
  }
}
