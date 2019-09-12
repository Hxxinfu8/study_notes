package com.company;

public interface MyMap<K, V> {
    V get(K key);
    V put(K key, V value);
}
