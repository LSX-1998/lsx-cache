package com.lsx.cache.core.support.listener.slow;

import com.alibaba.fastjson.JSON;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cahe.api.ICacheSlowListener;
import com.lsx.cahe.api.ICacheSlowListenerContext;

/**
 * 慢日志监听类
 */
public class CacheSlowListener implements ICacheSlowListener {

    private static final Log log = LogFactory.getLog(CacheSlowListener.class) ;

    @Override
    public void listen(ICacheSlowListenerContext context) {
        log.warn("[Slow] methodName: {}, params: {}, cost time: {}",
                context.getMethodName(), JSON.toJSON(context.getParams()), context.costTimeMills());
    }

    @Override
    public long slowerThanMills() {
        return 1000L;
    }
}
