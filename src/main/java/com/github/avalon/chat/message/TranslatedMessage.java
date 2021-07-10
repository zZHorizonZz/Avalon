package com.github.avalon.chat.message;

import com.github.avalon.language.Language;
import com.github.avalon.server.Bootstrap;

import java.text.MessageFormat;

/**
 * This message is implementation of {@link Message} and also extends it by automatically message
 * translation handling.
 *
 * @version 1.0
 */
public class TranslatedMessage extends Message {

  private final String path;
  private final Object[] arguments;

  private Language language = Language.EN_US;

  public TranslatedMessage(String path, Language language, Object... arguments) {
    this.path = path;
    this.arguments = arguments;

    setText(new MessageFormat(getTranslated(language)).format(arguments));
  }

  public TranslatedMessage(String path, Object... arguments) {
    this.path = path;
    this.arguments = arguments;

    setText(new MessageFormat(getTranslated(language)).format(arguments));
  }

  private String getTranslated(Language language) {
    String message =
        Bootstrap.INSTANCE
            .getServer()
            .getTranslationModule()
            .getMessageTranslations(language, path);
    if (message == null) {
      message =
          Bootstrap.INSTANCE
              .getServer()
              .getTranslationModule()
              .getMessageTranslations(Language.EN_US, path);
    }

    if (message == null) {
      message = "%red%Message not found in configuration. Please contact server administrator.";
    }

    return message;
  }

  public String getPath() {
    return path;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
    setText(new MessageFormat(getTranslated(language)).format(arguments));
  }
}
