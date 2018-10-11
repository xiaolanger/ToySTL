package com.xiaolanger.toy.tree;

import com.xiaolanger.toy.util.CostUtil;
import com.xiaolanger.toy.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class BTreeTest {
    private static final int BENCH_COUNT = 10000;
    private CostUtil costUtil = new CostUtil();
    private List<String> keys = RandomUtil.genRandomStringList(BENCH_COUNT);

    @Test
    public void bench1() {
        costUtil.start();
        BTree<String, String> tree = new BTree<>();
        // test put
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(i);
            tree.put(key, value);
        }

        // test remove
        for (String key : keys) {
            tree.remove(key);
        }
        costUtil.end();
    }

    @Test
    public void bench2() {
        costUtil.start();
        TreeMap<String, String> tree = new TreeMap<>();
        // test put
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(i);
            tree.put(key, value);
        }

        // test remove
        for (String key : keys) {
            tree.remove(key);
        }
        costUtil.end();
    }

    @Test
    public void bench3() {
        costUtil.start();
        BTree<String, String> tree = new BTree<>(false);
        // test put
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(i);
            tree.put(key, value);
        }

        // test remove
        for (String key : keys) {
            tree.remove(key);
        }
        costUtil.end();
    }

    @Test
    public void test() {
        List<String> keys = RandomUtil.genRandomStringList(10000);
        HashMap<String, String> map = new HashMap<>();
        BTree<String, String> tree = new BTree<>();

        // test put
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(i);
            map.put(key, value);
            tree.put(key, value);
        }
        // check size
        Assert.assertEquals(map.size(), tree.size());
        // check content
        for (String key : keys) {
            Assert.assertEquals(map.get(key), tree.get(key));
        }

        // test remove
        for (String key : keys) {
            int n = RandomUtil.nextInt(3);
            if (n == 0) {
                map.remove(key);
                tree.remove(key);
            }
        }
        // check size
        Assert.assertEquals(map.size(), tree.size());
        // check content
        for (String key : keys) {
            Assert.assertEquals(map.get(key), tree.get(key));
        }
    }
}
