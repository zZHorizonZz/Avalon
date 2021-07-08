package com.github.avalon.chat.message;

import com.github.avalon.language.Language;
import com.github.avalon.server.Bootstrap;

/**
 * This message is implementation of {@link Message} and also extends it by automatically message
 * translation handling.
 *
 * @version 1.0
 */
public class TranslatedMessage extends Message {

  private String path;

  public TranslatedMessage(String message) {
    super(message);
  }

  public TranslatedMessage getMessage(Language language) {
    return new TranslatedMessage(getTranslated(language));
  }

  public String getTranslated(Language language) {
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
}
