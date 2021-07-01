package com.github.avalon.console;

import java.util.logging.Level;

public enum LogLevel {
  DEBUG('*', 500),
  INFO('+', 800),
  WARN('?', 900),
  SEVERE('!', 1000);

  private final char character;
  private final int severity;

  LogLevel(char character, int severity) {
    this.character = character;
    this.severity = severity;
  }

  public static LogLevel getBySeverity(int severity) {
    if (severity == 500) {
      return LogLevel.DEBUG;
    } else if (severity == 800) {
      return LogLevel.INFO;
    } else if (severity == 900) {
      return LogLevel.WARN;
    } else if (severity == 1000) {
      return LogLevel.SEVERE;
    } else {
      return LogLevel.INFO;
    }
  }

  public static String formatLevel(Level level) {
    return "[" + getBySeverity(level.intValue()).getCharacter() + "]";
  }

  public char getCharacter() {
    return character;
  }

  public int getSeverity() {
    return severity;
  }
}
