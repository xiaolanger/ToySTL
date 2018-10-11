package com.xiaolanger.toy.list;

public class Array<V> {
    private V[] container;
    private int capacity;
    private int size;

    public Array(int capacity) {
        this.capacity = capacity;
        container = (V[]) new Object[capacity];
    }

    public Array() {
        capacity = 8;
        container = (V[]) new Object[capacity];
    }

    public V add(V value) {
        return add(size, value);
    }

    public V add(int index, V value) {
        if (index > size) {
            throw new RuntimeException("out of bounds");
        }

        if (size == capacity) {
            capacity = capacity * 2;
            V[] temp = (V[]) new Object[capacity];
            System.arraycopy(container, 0, temp, 0, size);
            container = temp;
        }

        if (index < size) {
            System.arraycopy(container, index, container, index + 1, size - index);
        }

        container[index] = value;
        size++;

        return value;
    }

    public int size() {
        return size;
    }

    public V[] toArray() {
        V[] array = (V[]) new Object[size];
        System.arraycopy(container, 0, array, 0, size);
        return array;
    }

    public V get(int index) {
        if (index >= size) {
            throw new RuntimeException("out of bounds");
        }

        return container[index];
    }

    public V remove(int index) {
        if (index >= size) {
            throw new RuntimeException("out of bounds");
        }

        System.arraycopy(container, index + 1, container, index, size - index - 1);
        size--;

        return null;
    }
}
