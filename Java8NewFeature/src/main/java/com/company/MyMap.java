package com.company;

/**
 * @author Upoint0002
 * @param <K>
 * @param <V>
 */
public interface MyMap<K, V> {
    V get(K key);
    V put(K key, V value);
}
