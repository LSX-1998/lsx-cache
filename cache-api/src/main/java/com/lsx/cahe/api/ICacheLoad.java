package com.lsx.cahe.api;

public interface ICacheLoad<K,V> {
    /**
     * 加载缓存信息
     * @param cache 缓存
     */
    void load(final ICache<K,V> cache);
}
