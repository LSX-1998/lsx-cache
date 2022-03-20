package com.lsx.cache.core.support.evict;

import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheEvictContext;


/**
 * 驱除策略
 *
 * 1. 新加的 key
 * 2. map 实现
 * 3. 淘汰监听器
 *
 * @author binbin.hou
 * @param <K> key
 * @param <V> value
 */
public class CacheEvictContext<K,V> implements ICacheEvictContext<K,V> {


    /**
     * 新加的 key
     */
    private K key;

    /**
     * cache 实现
     * @since 0.0.2
     */
    private ICache<K,V> cache;

    /**
     * 最大的大小
     * @since 0.0.2
     */
    private int size;

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public ICache<K,V> getCache() {
        return cache;
    }

    @Override
    public int getSize() {
        return size;
    }

    public CacheEvictContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    public CacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }

    public CacheEvictContext<K, V> cache(ICache<K, V> cache) {
        this.cache = cache;
        return this;
    }


}
