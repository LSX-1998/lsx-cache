package com.lsx.cache.core.support.Interceptor.aof;

import com.alibaba.fastjson.JSON;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cache.core.model.PersistAofEntry;
import com.lsx.cache.core.support.persist.CachePersistAof;
import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheInterceptor;
import com.lsx.cahe.api.ICacheInterceptorContext;
import com.lsx.cahe.api.ICachePersist;

public class CacheInterceptorAof<K,V> implements ICacheInterceptor<K,V> {

    private static final Log log = LogFactory.getLog(CacheInterceptorAof.class) ;

    @Override
    public void before(ICacheInterceptorContext<K, V> context) {

    }

    @Override
    public void after(ICacheInterceptorContext<K, V> context) {
        ICache<K,V> cache = context.cache() ;
        ICachePersist<K,V> persist = cache.persist() ;

        if(persist instanceof CachePersistAof){
            CachePersistAof<K,V> cachePersistAof = (CachePersistAof<K, V>) persist;
            String methodName = context.method().getName();
            PersistAofEntry aofEntry = PersistAofEntry.newInstance() ;
            aofEntry.setMethodName(methodName);
            aofEntry.setParams(context.params());

            String json = JSON.toJSONString(aofEntry) ;

            // 直接持久化
            log.debug("AOF 开始追加文件内容：{}", json);
            cachePersistAof.append(json);
            log.debug("AOF 完成追加文件内容：{}", json);

        }


    }
}
