package com.example.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class Message {
    private Type type;
    private HashMap<String, Object> body;

    private static final Gson gson = new GsonBuilder().create();

    /*
     * Empty constructor needed for JSON Serialization/Deserialization
     */
    public Message() {}

    public Message(HashMap<String, Object> body, Type type) {
        this.body = body;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }
    @SuppressWarnings("unchecked")
    public <T> T getFromBody(String key, Class<T> type) {
        Object raw = body.get(key);
        if (type.isEnum() && raw instanceof String) {
            return (T) Enum.valueOf((Class<Enum>) type.asSubclass(Enum.class), ((String) raw).toUpperCase());
        }
        return type.cast(raw);
    }

    public <T> T getObjectFromBody(String key, Class<T> clazz) {
        Object raw = body.get(key);
        if (raw == null) return null;

        // If it already is the correct type, just cast.
        if (clazz.isInstance(raw)) {
            return clazz.cast(raw);
        }

        // Otherwise raw is a LinkedTreeMap: turn it back into JSON...
        String json = gson.toJson(raw);
        // ...then parse it into your desired class
        return gson.fromJson(json, clazz);
    }

    public <T> T getObjectFromBody(String key, java.lang.reflect.Type targetType) {
        Object raw = body.get(key);
        if (raw == null) return null;
        String json = gson.toJson(raw);
        return gson.fromJson(json, targetType);
    }

    public void addToBody(String key, Object value) {
        body.put(key, value);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        COMMAND,
        RESPONSE,
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Object key : body.keySet()) {
            builder.append(key).append(": ").append(body.get(key)).append("\n");
        }
        return builder.toString();
    }
}
