package com.github.avalon.common.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonReader {

    @Nullable
    public static <T> T deserialize(Gson gson, Reader reader, Class<T> type, boolean lenient) {
        try {
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(reader);
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
