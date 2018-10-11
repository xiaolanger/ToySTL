package com.xiaolanger.toy.map;

import com.xiaolanger.toy.list.Array;
import com.xiaolanger.toy.list.Link;

public class LinkHash<K, V> {
    private class Pair<K, V> {
        private K key;
        private V value;
        private Pair<K, V> prev;
        private Pair<K, V> next;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Link<Pair<K, V>>[] container;
    private int size = 0;
    private Pair<K, V> head;
    private Pair<K, V> tail;

    public LinkHash() {
        container = new Link[31];
    }

    public void put(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        int slot = hash(key);
        if (container[slot] == null) {
            // add to the link
            if (tail == null) {
                head = pair;
                tail = pair;
            } else {
                tail.next = pair;
                pair.prev = tail;
                tail = pair;
            }
            // add pair
            Link<Pair<K, V>> link = new Link<>();
            link.add(pair);
            container[slot] = link;
            size++;
        } else {
            Link<Pair<K, V>> link = container[slot];
            if (link.size() > 8) {
                ensureCapacity();
                // real put after increase capacity
                put(key, value);
            } else {
                // add to the link
                if (tail == null) {
                    head = pair;
                    tail = pair;
                } else {
                    tail.next = pair;
                    pair.prev = tail;
                    tail = pair;
                }
                // add pair
                link.add(pair);
                size++;
            }
        }
    }

    public V get(K key) {
        int slot = hash(key);
        if (container[slot] == null) {
            return null;
        } else {
            Link<Pair<K, V>> link = container[slot];
            for (int i = 0; i < link.size(); i++) {
                Pair<K, V> pair = link.get(i);
                if (pair.key.equals(key)) {
                    return pair.value;
                }
            }
        }
        return null;
    }

    public V remove(K key) {
        int slot = hash(key);
        if (container[slot] == null) {
            return null;
        } else {
            Link<Pair<K, V>> link = container[slot];
            for (int i = 0; i < link.size(); i++) {
                Pair<K, V> pair = link.get(i);
                if (pair.key.equals(key)) {
                    // remove from the link
                    Pair<K, V> prev = pair.prev;
                    Pair<K, V> next = pair.next;
                    if (prev != null) {
                        prev.next = next;
                    } else {
                        head = next;
                    }
                    if (next != null) {
                        next.prev = prev;
                    } else {
                        tail = prev;
                    }
                    pair.prev = null;
                    pair.next = null;

                    // remove pair
                    V value = pair.value;
                    link.remove(i);
                    size--;
                    return value;
                }
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        // increase capacity
        container = new Link[container.length * 2];
        size = 0;
        Pair<K, V> p = head;
        head = tail = null;

        // rehash
        while (p != null) {
            put(p.key, p.value);
            p = p.next;
        }
    }

    private int hash(K key) {
        String str = key.toString();
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum = 31 * sum + str.charAt(i);
        }
        return Math.abs(sum % container.length);
    }

    public K[] keys() {
        Array<K> array = new Array<>(size);

        Pair<K, V> p = head;
        while (p != null) {
            array.add(p.key);
            p = p.next;
        }

        return array.toArray();
    }
}
