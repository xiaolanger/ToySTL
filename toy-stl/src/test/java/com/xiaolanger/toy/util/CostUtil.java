package com.xiaolanger.toy.util;

public class CostUtil {
    private long start;
    private long end;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end() {
        end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start));
    }
}
