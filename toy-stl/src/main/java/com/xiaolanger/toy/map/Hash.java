package com.xiaolanger.toy.map;

import com.xiaolanger.toy.list.Link;

public class Hash<K, V> {
    private class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Link<Pair<K, V>>[] container;
    private int size = 0;

    public Hash() {
        container = new Link[31];
    }

    public void put(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        int slot = hash(key);
        if (container[slot] == null) {
            Link<Pair<K, V>> link = new Link<>();
            link.add(pair);
            container[slot] = link;
            size++;
        } else {
            Link<Pair<K, V>> link = container[slot];
            if (link.size() > 8) {
                ensureCapacity();
                put(key, value);
            } else {
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
        Link<Pair<K, V>>[] temp = container;
        container = new Link[container.length * 2];
        size = 0;
        // rehash
        for (int i = 0; i < temp.length; i++) {
            Link<Pair<K, V>> link = temp[i];
            if (link != null) {
                for (int j = 0; j < link.size(); j++) {
                    Pair<K, V> pair = link.get(j);
                    put(pair.key, pair.value);
                }
            }
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
}
