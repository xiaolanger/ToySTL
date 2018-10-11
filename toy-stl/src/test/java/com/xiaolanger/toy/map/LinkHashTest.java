package com.xiaolanger.toy.map;

import com.xiaolanger.toy.list.Array;
import com.xiaolanger.toy.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

public class LinkHashTest {

    @Test
    public void test() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        LinkHash<String, String> hash = new LinkHash<>();
        List<String> keys = RandomUtil.genRandomStringList(10000);

        // test put
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(i);
            map.put(key, value);
            hash.put(key, value);
        }
        // check size
        Assert.assertEquals(map.size(), hash.size());
        // check content
        for (String key : keys) {
            Assert.assertEquals(map.get(key), hash.get(key));
        }

        // test remove
        for (String key : keys) {
            int n = RandomUtil.nextInt(3);
            if (n == 0) {
                map.remove(key);
                hash.remove(key);
            }
        }
        // check size
        Assert.assertEquals(map.size(), hash.size());
        // check content
        for (String key : keys) {
            Assert.assertEquals(map.get(key), hash.get(key));
        }

        // check keys size
        Array<String> ks = hash.keys();
        Assert.assertEquals(map.size(), ks.size());

        // check keys content
        int i = 0;
        for (String key : map.keySet()) {
            Assert.assertEquals(key, ks.get(i));
            i++;
        }
    }
}
