package com.xiaolanger.toy.map;

import com.xiaolanger.toy.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class HashTest {

    @Test
    public void test() {
        HashMap<String, String> map = new HashMap<>();
        Hash<String, String> hash = new Hash<>();
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
    }
}
