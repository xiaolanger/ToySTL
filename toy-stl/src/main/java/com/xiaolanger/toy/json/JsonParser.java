package com.xiaolanger.toy.json;

import com.xiaolanger.toy.list.Array;
import com.xiaolanger.toy.list.ByteLink;
import com.xiaolanger.toy.list.Link;
import com.xiaolanger.toy.map.LinkHash;

public class JsonParser {
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String NULL = "null";

    // current pointer
    private int p;
    // json bytes
    private byte[] bytes;

    private byte getChar() {
        if (p < bytes.length) {
            byte c = bytes[p];
            p++;
            return c;
        }
        return -1;
    }

    private void unGetChar() {
        p--;
    }

    public static String stringfy(Object json) {
        StringBuilder sb = new StringBuilder();
        if (json == null) {
            sb.append("null");
        } else if (json instanceof JsonArray) {
            JsonArray ja = (JsonArray) json;
            sb.append("[");
            for (int i = 0; i < ja.size(); i++) {
                sb.append(stringfy(ja.get(i))).append(",");
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
                sb.append("\"").append(key).append("\"").append(":").append(stringfy(jo.get(key))).append(",");
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
            throw new RuntimeException("illegal JsonParser Format");
        }
        return sb.toString();
    }

    public Object parse(String jsonString) {
        this.bytes = jsonString.getBytes();
        byte c = getChar();
        if (c == '{') {
            unGetChar();
            return parseObject();
        }
        if (c == '[') {
            unGetChar();
            return parseArray();
        }
        throw new RuntimeException("illegal json format");
    }

    private Object parseObject() {
        if (getChar() != '{') throw new RuntimeException("illegal start of JsonObject");

        if (getChar() == '}') {
            return null;
        } else {
            unGetChar();
        }

        LinkHash<String, Object> map = new LinkHash<>();
        while (true) {
            String key = (String) parseString();
            if (getChar() != ':') throw new RuntimeException("illegal JsonObject");
            Object value = parseValue();
            map.put(key, value);
            if (getChar() == ',') {
                continue;
            } else {
                unGetChar();
                break;
            }
        }

        if (getChar() != '}') throw new RuntimeException("illegal end of JsonObject");
        return new JsonObject(map);
    }

    private Object parseString() {
        if (getChar() != '"') throw new RuntimeException("illegal start of String");

        ByteLink array = new ByteLink();
        byte c;
        while ((c = getChar()) != '"') {
            if (c == -1) {
                throw new RuntimeException("illegal end of String");
            }
            array.add(c);
        }

        return new String(array.toArray());
    }

    private Object parseNumber() {
        byte c = getChar();

        if (!Character.isDigit(c) && c != '-') throw new RuntimeException("illegal start of Number");

        ByteLink array = new ByteLink();

        boolean positive;
        // consume '-'
        if (c == '-') {
            positive = false;
            c = getChar();
        } else {
            positive = true;
        }

        while (Character.isDigit(c)) {
            array.add(c);
            c = getChar();
        }

        if (array.size() > 1 && array.get(0) == '0') throw new RuntimeException("illegal start of Number");

        if (c != '.') {
            unGetChar();
            if (!positive) {
                array.add(0, (byte) '-');
            }
            return Integer.valueOf(new String(array.toArray()));
        } else {
            array.add(c);
            c = getChar();

            if (!Character.isDigit(c)) throw new RuntimeException("illegal start of Number");

            while (Character.isDigit(c)) {
                array.add(c);
                c = getChar();
            }
            unGetChar();
            if (!positive) {
                array.add(0, (byte) '-');
            }
            return Double.valueOf(new String(array.toArray()));
        }
    }

    private Object parseBool() {
        byte c = getChar();

        if (c == 't') {
            // true
            for (int i = 1; i < TRUE.length(); i++) {
                if (getChar() != TRUE.charAt(i)) {
                    throw new RuntimeException("illegal Bool format");
                }
            }
            return true;
        }

        if (c == 'f') {
            // false
            for (int i = 1; i < FALSE.length(); i++) {
                if (getChar() != FALSE.charAt(i)) {
                    throw new RuntimeException("illegal Bool format");
                }
            }
            return false;
        }

        throw new RuntimeException("illegal Bool format");
    }

    private Object parseNull() {
        if (getChar() == 'n') {
            // null
            for (int i = 1; i < NULL.length(); i++) {
                if (getChar() != NULL.charAt(i)) {
                    throw new RuntimeException("illegal null format");
                }
            }
            return null;
        }

        throw new RuntimeException("illegal null format");
    }

    private Object parseValue() {
        Object object;

        byte c = getChar();
        if (c == '"') {
            unGetChar();
            object = parseString();
        } else if (c == '{') {
            unGetChar();
            object = parseObject();
        } else if (c == '[') {
            unGetChar();
            object = parseArray();
        } else if (Character.isDigit(c) || c == '-') {
            unGetChar();
            object = parseNumber();
        } else if (c == 't' || c == 'f') {
            unGetChar();
            object = parseBool();
        } else if (c == 'n') {
            unGetChar();
            object = parseNull();
        } else {
            throw new RuntimeException("illegal start of JsonValue");
        }

        return object;
    }

    private Object parseArray() {
        if (getChar() != '[') throw new RuntimeException("illegal start of JsonArray");

        if (getChar() == ']') {
            return null;
        } else {
            unGetChar();
        }

        Link<Object> list = new Link<>();
        while (true) {
            Object object = parseValue();
            list.add(object);
            if (getChar() == ',') {
                continue;
            } else {
                unGetChar();
                break;
            }
        }

        if (getChar() != ']') throw new RuntimeException("illegal end of JsonArray");
        return new JsonArray(list);
    }

}
