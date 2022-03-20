package com.lsx.cahe.api;
/**
 * 缓存明细信息
 * @param <K> key
 * @param <V> value
 */
public interface ICacheEntry<K,V> {
    /**
     * @return key
     */
    K getKey();

    /**
     * @return value
     */
    V getValue();
}
