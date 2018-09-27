package com.xiaolanger.toy.sort;

import java.util.List;

public class MergeSort {
    public static <T extends Comparable<T>> void mergeSort(List<T> list) {
        mergeSort(list, 0, list.size());
    }

    private static <T extends Comparable<T>> void mergeSort(List<T> list, int s, int e) {
        int mid = s + (e - s) / 2;
        if (mid > s && e > mid) {
            mergeSort(list, s, mid);
            mergeSort(list, mid, e);
            merge(list, s, mid, mid, e);
        }
    }

    private static <T extends Comparable<T>> void merge(List<T> list, int s1, int e1, int s2, int e2) {
        int p1 = s1;
        int p2 = s2;

        T temp[] = (T[]) new Comparable[(e1 - s1) + (e2 - s2)];
        int pos = 0;
        while (p1 != e1 || p2 != e2) {
            if (p1 == e1) {
                temp[pos] = list.get(p2);
                pos++;
                p2++;
                continue;
            }
            if (p2 == e2) {
                temp[pos] = list.get(p1);
                pos++;
                p1++;
                continue;
            }
            if (list.get(p1).compareTo(list.get(p2)) < 0) {
                temp[pos] = list.get(p1);
                pos++;
                p1++;
            } else {
                temp[pos] = list.get(p2);
                pos++;
                p2++;
            }
        }

        for (int i = s1; i < e2; i++) {
            list.set(i, temp[i - s1]);
        }
    }
}
