package com.github.avalon.snbt.exception;

public class ReadException extends RuntimeException {

  public ReadException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
