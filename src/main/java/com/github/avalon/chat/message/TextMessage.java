package com.github.avalon.chat.message;

import java.text.MessageFormat;

public class TextMessage extends Message {

  private final String text;

  public TextMessage(String text, Object... arguments) {
    this.text = new MessageFormat(text).format(arguments);

    setText(this.text);
  }

  public String getText() {
    return text;
  }
}
