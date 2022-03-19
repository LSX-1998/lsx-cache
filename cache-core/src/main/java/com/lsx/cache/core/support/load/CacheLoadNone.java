package com.lsx.cache.core.support.load;

import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheLoad;

public class CacheLoadNone<K,V> implements ICacheLoad<K,V> {
    @Override
    public void load(ICache<K, V> cache) {

    }
}
