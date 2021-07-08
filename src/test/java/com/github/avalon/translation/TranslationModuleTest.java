package com.github.avalon.translation;

import com.github.avalon.language.Language;
import com.github.avalon.resource.data.ResourceJson;
import com.github.avalon.server.MockNetworkServer;
import org.junit.jupiter.api.Assertions;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.Map;

public class TranslationModuleTest {

  @Benchmark
  public void testCreationTranslated() {
    TranslationModule module = new TranslationModule(new MockNetworkServer());
    Map<Language, ResourceJson> languageTestMap = module.getTranslations();
    languageTestMap.put(
        Language.EN_US, new ResourceJson("{ \"test\": { \"test\": \"Test insert\"}}"));

    for (int i = 0; i < 1000; i++) {
      Assertions.assertEquals( 1, module.getMessageTranslations("test.test").size());
    }
  }
}
