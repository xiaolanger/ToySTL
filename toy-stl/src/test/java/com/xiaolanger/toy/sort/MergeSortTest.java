package com.xiaolanger.toy.sort;

import com.xiaolanger.toy.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSortTest {
    @Test
    public void test() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (Integer i : RandomUtil.genRandomIntList(10000)) {
            list1.add(i);
            list2.add(i);
        }
        Collections.sort(list1);
        MergeSort.mergeSort(list2);
        // check content
        for (int i = 0; i < list1.size(); i++) {
            Assert.assertEquals(list1.get(i), list2.get(i));
        }
    }
}
