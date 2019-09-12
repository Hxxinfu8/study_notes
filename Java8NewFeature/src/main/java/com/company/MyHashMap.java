package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;// 16
    private int initialCapacity = 0;
    private Node<K, V>[] table = null;

    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        table = new Node[initialCapacity];
    }

    public MyHashMap(){
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        table = new Node[initialCapacity];
    }

    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("123", "121231");
        map.put("112312", "121312");
        System.out.println(map.get("123"));
        System.out.println(map.get("112312"));
        map.put("123", "3");
        map.put(null, null);
        System.out.println(map.get("123"));
        System.out.println(map.get(null));
    }


    @Override
    public V get(K key) {
        int index = hash(key) % initialCapacity;
        Node<K, V> node = table[index];
        if (node == null) {
            return null;
        }

        while (node != null) {
            if (node.key == null && key == null || Objects.equals(hash(key), hash(node.key))) {
                return node.value;
            }

            node = node.next;
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key) % initialCapacity;
        if (table[index] != null) {
            Node<K, V> hasNode = table[index];
            Node<K, V> n = null;
            while (hasNode != null) {
                if (hash(hasNode.key) == hash(key)) {
                    hasNode.value = value;
                    return value;
                }

                n = hasNode;
                hasNode = hasNode.next;
            }
            n.next = new Node<K, V>(key, value, null, index);
        } else {
            Node<K, V> n = new Node<K, V>(key, value, null, index);
            table[index] = n;
        }
        return value;
    }

    public int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    class Node<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Node<K, V> next;
        int index;

        Node(K k, V v, Node<K, V> next, int index) {
            this.key = k;
            this.value = v;
            this.next = next;
            this.index = index;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public Node<K, V> getNext() {
            return this.next;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (o instanceof Map.Entry) {
                Map.Entry<K, V> e = (Map.Entry<K, V>) o;
                if (Objects.equals(this.key, e.getKey())
                        && Objects.equals(this.value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }
}
