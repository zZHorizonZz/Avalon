package com.github.avalon.common.exception;

public class NotSupportedException extends RuntimeException {
  static final long serialVersionUID = -545616541314357435L;

  public NotSupportedException() {}

  public NotSupportedException(String message) {
    super(message);
  }

  public NotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotSupportedException(Throwable cause) {
    super(cause);
  }
}
