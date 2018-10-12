package com.xiaolanger.toy.json;

import com.xiaolanger.toy.list.Link;

public class JsonArray {
    private JsonParser parser = new JsonParser();
    private Link<Object> json;

    public JsonArray() {
        this.json = new Link<>();
    }

    public JsonArray(Link<Object> json) {
        this.json = json;
    }

    public JsonArray(String jsonString) {
        Object o = parser.parse(jsonString);
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

    public Object get(int position) {
        return json.get(position);
    }

    public int size() {
        return json.size();
    }

}
