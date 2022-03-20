package com.lsx.cache.core.support.Interceptor.evict;


import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cahe.api.ICacheEvict;
import com.lsx.cahe.api.ICacheInterceptor;
import com.lsx.cahe.api.ICacheInterceptorContext;

import java.lang.reflect.Method;

public class CacheInterceptorEvict<K,V> implements ICacheInterceptor<K,V> {

    private static final Log log = LogFactory.getLog(ICacheInterceptor.class) ;

    @Override
    public void before(ICacheInterceptorContext<K, V> context) {

    }

    @Override
    @SuppressWarnings("all")
    public void after(ICacheInterceptorContext<K, V> context) {
        ICacheEvict<K,V> evict = context.getCache().getEvict();

        Method method = context.getMethod();
        final K key = (K) context.getParams()[0];
        if("remove".equals(method.getName())) {
            evict.removeKey(key);
        } else {
            evict.updateKey(key);
        }
    }
}
