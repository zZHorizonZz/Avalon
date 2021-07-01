package com.github.avalon.console.logging;

import com.github.avalon.console.ConsoleManager;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.attributes.Status;
import com.github.avalon.server.Bootstrap;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultLogger {

  private final Logger logger = ConsoleManager.LOGGER;

  public DefaultLogger(String name) {
    //logger = LogManager.getLogManager().getLogger(name);
  }

  public DefaultLogger(Class<?> clazz) {
    //logger = LogManager.getLogManager().getLogger(clazz.getSimpleName());
  }

  public void debugToPlayer(IPlayer player, String message, Object... objects) {
    if (Bootstrap.INSTANCE.getServer().getServerData().isDeveloperMode()) {
      logger.log(Level.FINE, message, objects);

      if (player != null && player.getPlayerStatus().equals(Status.ONLINE)) {
        player.sendSystemMessage("%gold%Debug%gray%> %white%" + String.format(message, objects));
      }
    }
  }

  public void infoToPlayer(IPlayer player, String message, Object... objects) {
    logger.log(Level.INFO, message, objects);

    if (player != null && player.getPlayerStatus().equals(Status.ONLINE)) {
      player.sendSystemMessage("%aqua%Info%gray%> %white%" + String.format(message, objects));
    }
  }

  public void warningToPlayer(IPlayer player, String message, Object... objects) {
    logger.log(Level.WARNING, String.format(message, objects));

    if (player != null && player.getPlayerStatus().equals(Status.ONLINE)) {
      player.sendSystemMessage("%yellow%Warn%gray%> %white%" + String.format(message, objects));
    }
  }

  public void errorToPlayer(
      IPlayer player, String message, Throwable throwable, Object... objects) {
    logger.log(Level.SEVERE, String.format(message, objects), throwable);
    if (player != null && player.getPlayerStatus().equals(Status.ONLINE)) {
      player.sendSystemMessage("%red%Error%gray%> %white%" + String.format(message, objects));
    }
  }

  public void debug(String message, Object... objects) {
    if (Bootstrap.INSTANCE.getServer().getServerData().isDeveloperMode()) {
      logger.log(Level.FINE, String.format(message, objects));
    }
  }

  public void info(String message, Object... objects) {
    logger.log(Level.INFO, String.format(message, objects));
  }

  public void warning(String message, Object... objects) {
    logger.log(Level.WARNING, String.format(message, objects));
  }

  public void error(String message, Throwable throwable, Object... objects) {
    logger.log(Level.SEVERE, String.format(message, objects), throwable);
  }

  public Logger getLogger() {
    return logger;
  }
}
