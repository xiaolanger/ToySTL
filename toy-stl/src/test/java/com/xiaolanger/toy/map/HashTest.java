package com.xiaolanger.toy.map;

import com.xiaolanger.toy.list.Array;
import com.xiaolanger.toy.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
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

        Array<String> ks = hash.keys();
        String[] ks1 = new String[hash.size()];
        String[] ks2 = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            ks1[i] = ks.get(i);
            ks2[i] = key;
            i++;
        }
        Arrays.sort(ks1);
        Arrays.sort(ks2);

        // check keys size
        Assert.assertEquals(ks2.length, ks1.length);
        // check keys content
        for (int j = 0; j < ks2.length; j++) {
            Assert.assertEquals(ks2[j], ks1[j]);
        }
    }
}
