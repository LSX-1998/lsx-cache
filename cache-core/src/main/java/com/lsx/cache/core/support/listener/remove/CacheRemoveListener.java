package com.lsx.cache.core.support.listener.remove;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cahe.api.ICacheRemoveListener;
import com.lsx.cahe.api.ICacheRemoveListenerContext;

public class CacheRemoveListener<K,V> implements ICacheRemoveListener<K,V> {

    private static final Log log = LogFactory.getLog(CacheRemoveListener.class) ;

    @Override
    public void listen(ICacheRemoveListenerContext<K,V> context) {

        log.debug("Remove key: {}, value: {}, type: {}",
                context.getKey(), context.getValue(), context.getType());

    }
}
