package com.github.avalon.translation;

import com.github.avalon.language.Language;
import com.github.avalon.module.ServerModule;
import com.github.avalon.resource.data.ResourceJson;
import com.github.avalon.server.IServer;

import java.util.EnumMap;

public class TranslationModule extends ServerModule {

  private final EnumMap<Language, ResourceJson> translations;

  public TranslationModule(IServer host) {
    super(host);

    translations = new EnumMap<>(Language.class);
  }

  @Override
  public void enable() {
    super.enable();


  }

  /**
   * Register resource language to translation map. If language is already registered it will be
   * overwritten. If we want to add new message to already existing translation we should get {@link
   * ResourceJson} from map and then directly register this message.
   *
   * @param language Language that will be registered.
   * @param json {@link ResourceJson} is object that will map json and convert it into map.
   */
  public void registerLanguageResource(Language language, ResourceJson json) {
    translations.put(language, json);
  }

  /**
   * Register resource language to translation map. If language is already registered it will be
   * overwritten. If we want to add new message to already existing translation we should get {@link
   * ResourceJson} from map and then directly register this message.
   *
   * @param language Language that will be registered.
   * @param json {@link ResourceJson} is object that will map json and convert it into map.
   */
  public void registerLanguageResource(Language language, String json) {
    translations.put(language, new ResourceJson(json));
  }

  /**
   * If language is already registered and contains message with identifier then this message will
   * be returned otherwise null is returned.
   *
   * @param language Language that we want to get message from.
   * @param identifier Identifier of message.
   * @return Message if exist, null if not.
   */
  public String getMessageTranslations(Language language, String identifier) {
    ResourceJson resource = translations.get(language);
    if (resource == null) {
      return null;
    }

    return resource.getMessage(identifier);
  }

  /**
   * Returns {@link ResourceJson} that contains registered messages for specified {@link Language}
   * if there is nothing registered under specifier language then null is returned.
   *
   * @param language Language that we want to return its messages.
   * @return {@link ResourceJson} containing messages.
   */
  public ResourceJson getResource(Language language) {
    return translations.get(language);
  }

  public EnumMap<Language, ResourceJson> getTranslations() {
    return translations;
  }
}
