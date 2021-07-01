package com.github.avalon.chat.message;

public class MessageFactory {

  public static final int MAX_COLOR_LENGTH_NAME = 32;

  private String text;

  private Message message = new Message();
  private Text segment = new Text();
  private StringBuilder currentText = new StringBuilder();

  private char[] chars;
  private boolean textLocked;

  private StringBuilder currentColor = new StringBuilder();
  private boolean format;

  private int currentColorLength;

  public MessageFactory(String text) {
    this.text = text;

    chars = text.toCharArray();
  }

  public Message toChat() {
    for (int i = 0; i < chars.length; i++) {
      char currentChar = chars[i];
      if (currentChar == '%' && (i == 0 || (i - 1 > 0 && chars[i - 1] != '\\'))) {
        validateIdentifier();
        continue;
      }

      if (format && !textLocked && currentChar != '%') {
        currentColor.append(currentChar);
        currentColorLength++;

        if (currentColorLength > MAX_COLOR_LENGTH_NAME) {
          currentColorLength = 0;
          format = false;
          textLocked = true;
          currentText.append(currentColor);
        }
        continue;
      }

      textLocked = true;
      currentText.append(currentChar);
    }

    reset();
    return message;
  }

  private void validateIdentifier() {
    if (!textLocked) {
      if (!format) {
        format = true;
      } else {
        format = false;

        ChatColor color = ChatColor.BY_NAME.get(currentColor.toString());
        ChatEffect effect = ChatEffect.getByName(currentColor.toString());
        if (color != null) {
          segment.setColor(color);
        } else if (effect != null) {
          segment.addEffect(effect);
        } else {
          color = ChatColor.fromHex(currentColor.toString());
          segment.setColor(color);
        }

        currentColor = new StringBuilder();
      }

      return;
    }

    reset();
  }

  private void reset() {
    segment.setText(currentText.toString());
    message.append(segment);
    segment = new Text();
    currentText = new StringBuilder();
    textLocked = false;
    currentColor = new StringBuilder();
    format = true;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public Text getSegment() {
    return segment;
  }

  public void setSegment(Text segment) {
    this.segment = segment;
  }

  public StringBuilder getCurrentText() {
    return currentText;
  }

  public void setCurrentText(StringBuilder currentText) {
    this.currentText = currentText;
  }

  public char[] getChars() {
    return chars;
  }

  public void setChars(char[] chars) {
    this.chars = chars;
  }

  public boolean isTextLocked() {
    return textLocked;
  }

  public void setTextLocked(boolean textLocked) {
    this.textLocked = textLocked;
  }

  public StringBuilder getCurrentColor() {
    return currentColor;
  }

  public void setCurrentColor(StringBuilder currentColor) {
    this.currentColor = currentColor;
  }

  public boolean isFormat() {
    return format;
  }

  public void setFormat(boolean format) {
    this.format = format;
  }
}
