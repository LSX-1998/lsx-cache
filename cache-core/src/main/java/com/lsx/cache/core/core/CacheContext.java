package com.lsx.cache.core.core;



import com.lsx.cahe.api.ICacheContext;
import com.lsx.cahe.api.ICacheEvict;

import java.util.Map;

/**
 * 缓存上下文
 */
public class CacheContext<K,V> implements ICacheContext<K, V> {

    /**
     * map 信息
     */
    private Map<K, V> map;

    /**
     * 大小限制
     */
    private int size;

    /**
     * 驱除策略
     */
    private ICacheEvict<K,V> cacheEvict;



    public CacheContext<K, V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
    }

    public CacheContext<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    @Override
    public Map<K, V> map() {
        return map ;
    }



    @Override
    public int size() {
        return size ;
    }

    @Override
    public ICacheEvict<K, V> cacheEvict() {
        return cacheEvict ;
    }
}
