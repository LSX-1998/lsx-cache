package com.lsx.cahe.api;

public interface ICacheEvictContext<K,V> {

    /**
     * 新加的 key
     * @return key
     */
    K key();

    /**
     * cache 实现
     * @return map
     */
    ICache<K, V> cache();

    /**
     * 获取大小
     * @return 大小
     */
    int size();
}
