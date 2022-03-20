package com.lsx.cache.core.model;


import com.lsx.cahe.api.ICacheEntry;

/**
 * key value 的明细信息
 * @param <K> key
 * @param <V> value
 */
public class CacheEntry<K,V> implements ICacheEntry<K,V> {

    /**
     * key
     */
    private final K key;

    /**
     * value
     */
    private final V value;

    /**
     * 新建元素
     * @param key key
     * @param value value
     * @param <K> 泛型
     * @param <V> 泛型
     * @return 结果
     */
    public static <K,V> CacheEntry<K,V> of(final K key, final V value) {
        return new CacheEntry<>(key, value);
    }

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "EvictEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

}
