package com.xiaolanger.toy.json;

import com.xiaolanger.toy.map.Hash;

public class JsonObject {
    private Hash json;

    public JsonObject(Hash json) {
        this.json = json;
    }

    public JsonObject(String jsonString) {
        Object o = new JsonReader(jsonString).parseJson();
        JsonObject jsonObject = (JsonObject) o;
        this.json = jsonObject.json;
    }

    public JsonObject put(String key, Object value) {
        json.put(key, value);
        return this;
    }

    public JsonObject getJsonObject(String key) {
        return (JsonObject) json.get(key);
    }

    public JsonArray getJsonArray(String key) {
        return (JsonArray) json.get(key);
    }

    public String getString(String key) {
        return String.valueOf(json.get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(getString(key));
    }

    public boolean getBool(String key) {
        return Boolean.parseBoolean(getString(key));
    }
}
