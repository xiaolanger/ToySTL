package com.xiaolanger.toy.list;

import com.xiaolanger.toy.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class LinkTest {

    @Test
    public void test() {
        Link<String> link = new Link<>();
        LinkedList<String> list = new LinkedList<>();

        // test add
        for (int i = 0; i < 10000; i++) {
            String str = String.valueOf(i);
            // random add
            int index = RandomUtil.nextInt(i + 1);
            list.add(index, str);
            link.add(index, str);
        }
        // check add size
        Assert.assertEquals(list.size(), link.size());
        // check add content
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i), link.get(i));
        }

        // test remove
        for (int i = list.size() - 1; i >= 0; i--) {
            // random remove
            int n = RandomUtil.nextInt(3);
            if (n == 0) {
                list.remove(i);
                link.remove(i);
            }
        }
        // check remove size
        Assert.assertEquals(list.size(), link.size());
        // check remove content
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i), link.get(i));
        }
    }
}
