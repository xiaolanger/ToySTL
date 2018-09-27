package com.xiaolanger.toy.json;

import com.xiaolanger.toy.list.ByteLink;
import com.xiaolanger.toy.list.Link;
import com.xiaolanger.toy.map.Hash;

public class Json {
    // current pointer
    private int p;
    // json bytes
    private byte[] bytes;

    public Json(String jsonString) {
        this.bytes = jsonString.getBytes();
    }

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

    public Object parseJson() {
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

        Hash<String, Object> map = new Hash<>();
        while (true) {
            String key = parseString();
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
        return map;
    }

    private String parseString() {
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
        while (Character.isDigit(c) || c == '.' || c == '-') {
            array.add(c);
            c = getChar();
        }
        unGetChar();

        try {
            String number = new String(array.toArray());
            if (number.startsWith("00") || number.endsWith("."))
                throw new RuntimeException("illegal Number format");
            return Double.valueOf(number);
        } catch (NumberFormatException e) {
            throw new RuntimeException("illegal Number format");
        }
    }

    private Object parseBool() {
        ByteLink array = new ByteLink();
        array.add(getChar());
        array.add(getChar());
        array.add(getChar());
        array.add(getChar());
        if (new String(array.toArray()).equals("true")) return true;

        array.add(getChar());
        if (new String(array.toArray()).equals("false")) return false;

        throw new RuntimeException("illegal Bool format");
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
        } else {
            throw new RuntimeException("illegal start of JsonValue");
        }

        return object;
    }

    private Link<Object> parseArray() {
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
        return list;
    }

}