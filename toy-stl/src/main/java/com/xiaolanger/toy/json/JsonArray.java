package com.xiaolanger.toy.json;

import com.xiaolanger.toy.list.Link;

public class JsonArray {
    private Link json;

    public JsonArray(Link json) {
        this.json = json;
    }

    public JsonArray(String jsonString) {
        Object o = new JsonReader(jsonString).parseJson();
        JsonArray jsonArray = (JsonArray) o;
        this.json = jsonArray.json;
    }

    public JsonArray add(Object value) {
        json.add(value);
        return this;
    }

    public JsonObject getJsonObject(int position) {
        return (JsonObject) json.get(position);
    }

    public JsonArray getJsonArray(int position) {
        return (JsonArray) json.get(position);
    }

    public String getString(int position) {
        return String.valueOf(json.get(position));
    }

    public int getInt(int position) {
        return Integer.parseInt(getString(position));
    }

    public double getDouble(int position) {
        return Double.parseDouble(getString(position));
    }

    public boolean getBool(int position) {
        return Boolean.parseBoolean(getString(position));
    }
}