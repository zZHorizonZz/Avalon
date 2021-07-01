package com.github.avalon.common.json;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public abstract class ObjectSerializer<O> implements JsonDeserializer<O>, JsonSerializer<O> {

  @Nullable
  public static <T> T deserialize(Gson gson, Reader reader, Class<T> type, boolean lenient) {
    try {
      JsonReader jsonReader = new JsonReader(reader);
      jsonReader.setLenient(lenient);
      return gson.getAdapter(type).read(jsonReader);
    } catch (IOException exception) {
      throw new JsonParseException(exception);
    }
  }

  @Nullable
  public static <T> T deserialize(Gson gson, String json, Class<T> type, boolean lenient) {
    return deserialize(gson, new StringReader(json), type, lenient);
  }

  @Nullable
  public static <T> T deserialize(Gson gson, Reader reader, Class<T> type) {
    return deserialize(gson, reader, type, false);
  }

  @Nullable
  public static <T> T deserialize(Gson gson, String json, Class<T> type) {
    return deserialize(gson, json, type, false);
  }
}
