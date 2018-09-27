package com.xiaolanger.toy;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Test {
    private String getException(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter w  = new PrintWriter(sw);
        Throwable t = throwable;
        while (t != null) {
            if (t.getStackTrace() != null) {
                t.printStackTrace(w);
            }
            t = t.getCause();
        }
        return sw.toString();
    }

    private static Map<String, String> parseQuery(String url) {
        HashMap<String, String> params = new HashMap<>();

        if (url == null) {
            return params;
        }

        String[] splitByQuestionMark = url.split("\\?");
        if (splitByQuestionMark.length != 2) {
            return params;
        }

        String query = splitByQuestionMark[1];
        String kvs[] = query.split("&");
        for (String kv : kvs) {
            String pair[] = kv.split("=");
            if (pair.length != 2) {
                continue;
            }
            params.put(pair[0], pair[1]);
        }

        return params;
    }
    public static void main(String[] args) {
        Map<String, String> params = parseQuery(null);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }

        String str = "0.719999999999999970";
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(Double.valueOf(str)));
        System.out.println(bigDecimal.setScale(2, RoundingMode.FLOOR).toString());

        BigDecimal bigDecimal1 = new BigDecimal(str);
        System.out.println(bigDecimal1.setScale(2, RoundingMode.FLOOR).toString());
    }
}
