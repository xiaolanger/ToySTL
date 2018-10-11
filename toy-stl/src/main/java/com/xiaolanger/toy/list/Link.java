package com.xiaolanger.toy.list;

public class Link<V> {
    private class Node<V> {
        private V value;
        private Node<V> prev;
        private Node<V> next;

        public Node(V value) {
            this.value = value;
        }
    }

    private Node<V> header = null;
    private int size;

    public V add(V value) {
        return add(size, value);
    }

    public V add(int index, V value) {
        if (index > size) {
            throw new RuntimeException("out of bounds");
        }

        if (header == null) {
            header = new Node<>(value);
            size++;
            return value;
        }

        Node<V> p = header;
        // move to index or tail
        for (int i = 1; i <= index; i++) {
            if (p.next != null) {
                p = p.next;
            }
        }

        if (index == size) {
            // deal tail
            Node<V> node = new Node<>(value);
            node.prev = p;
            p.next = node;
        } else if (index == 0) {
            // deal header
            Node<V> node = new Node<>(value);
            node.next = p;
            p.prev = node;
            header = node;
        } else {
            Node<V> prev = p.prev;

            // deal middle
            Node<V> node = new Node<>(value);
            prev.next = node;
            node.prev = prev;

            node.next = p;
            p.prev = node;
        }

        size++;

        return value;
    }

    public int size() {
        return size;
    }

    public V get(int index) {
        if (index >= size) {
            throw new RuntimeException("out of bounds");
        }

        Node<V> p = header;
        // move to index
        for (int i = 1; i <= index; i++) {
            p = p.next;
        }

        return p.value;
    }

    public V remove(int index) {
        if (index >= size) {
            throw new RuntimeException("out of bounds");
        }

        Node<V> p = header;
        // move to index
        for (int i = 1; i <= index; i++) {
            p = p.next;
        }

        Node<V> prev = p.prev;
        Node<V> next = p.next;

        // delete node
        p.prev = null;
        p.next = null;

        if (prev != null && next != null) {
            // relink
            prev.next = next;
            next.prev = prev;
            size--;
            return p.value;
        }

        if (prev != null) {
            // relink
            prev.next = null;
            size--;
            return p.value;
        }

        if (next != null) {
            // relink
            next.prev = null;
            header = next;
            size--;
            return p.value;
        }

        header = null;
        size--;
        return p.value;
    }
}
