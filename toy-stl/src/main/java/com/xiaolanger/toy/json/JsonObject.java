package com.xiaolanger.toy.json;

import com.xiaolanger.toy.list.Array;
import com.xiaolanger.toy.map.LinkHash;

public class JsonObject {
    private JsonParser parser = new JsonParser();
    private LinkHash<String, Object> json;

    public JsonObject() {
        this.json = new LinkHash<>();
    }

    public JsonObject(LinkHash<String, Object> json) {
        this.json = json;
    }

    public JsonObject(String jsonString) {
        Object o = parser.parse(jsonString);
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

    public Object get(String key) {
        return json.get(key);
    }

    public int size() {
        return json.size();
    }

    public Array<String> keys() {
        return json.keys();
    }
}
