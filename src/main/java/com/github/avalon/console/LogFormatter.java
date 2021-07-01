package com.github.avalon.console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  @Override
  public String format(LogRecord record) {
    StringBuilder builder = new StringBuilder();
    builder.append(ANSI_WHITE);

    builder.append('[');
    builder.append(calcDate(record.getMillis()));
    builder.append("] ");

    builder.append(LogLevel.formatLevel(record.getLevel()));

    builder.append(ANSI_WHITE);
    builder.append(' ');
    builder.append(record.getMessage());

    Object[] params = record.getParameters();

    if (params != null) {
      builder.append('\t');
      for (int i = 0; i < params.length; i++) {
        builder.append(params[i]);
        if (i < params.length - 1) {
          builder.append(", ");
        }
      }
    }

    builder.append(ANSI_RESET);
    builder.append('\n');
    return builder.toString();
  }

  private String calcDate(long milliseconds) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date resultDate = new Date(milliseconds);
    return dateFormat.format(resultDate);
  }
}
