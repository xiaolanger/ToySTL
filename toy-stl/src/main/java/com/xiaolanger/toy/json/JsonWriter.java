package com.xiaolanger.toy.json;

import com.xiaolanger.toy.list.Array;

public class JsonWriter {
    public static String write(Object json) {
        StringBuilder sb = new StringBuilder();
        if (json == null) {
            sb.append("null");
        } else if (json instanceof JsonArray) {
            JsonArray ja = (JsonArray) json;
            sb.append("[");
            for (int i = 0; i < ja.size(); i++) {
                sb.append(write(ja.get(i))).append(",");
            }
            if (ja.size() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
        } else if (json instanceof JsonObject) {
            JsonObject jo = (JsonObject) json;
            sb.append("{");
            Array<String> keys = jo.keys();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                sb.append("\"").append(key).append("\"").append(":").append(write(jo.get(key))).append(",");
            }
            if (keys.size() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("}");
        } else if (json instanceof String) {
            sb.append("\"").append(json).append("\"");
        } else if (json instanceof Number || json instanceof Boolean) {
            sb.append(String.valueOf(json));
        } else {
            throw new RuntimeException("illegal Json Format");
        }
        return sb.toString();
    }

}
