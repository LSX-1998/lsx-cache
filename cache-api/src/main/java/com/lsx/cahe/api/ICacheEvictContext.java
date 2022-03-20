package com.lsx.cahe.api;

public interface ICacheEvictContext<K,V> {

    /**
     * 新加的 key
     * @return key
     */
    K getKey();

    /**
     * cache 实现
     * @return map
     */
    ICache<K, V> getCache();

    /**
     * 获取大小
     * @return 大小
     */
    int getSize();
}
