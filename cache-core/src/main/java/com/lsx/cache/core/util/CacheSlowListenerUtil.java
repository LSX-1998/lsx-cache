package com.lsx.cache.core.util;

import com.lsx.cache.core.support.listener.slow.CacheSlowListener;
import com.lsx.cahe.api.ICacheSlowListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 慢日志监听工具类
 * @author binbin.hou
 * @since 0.0.9
 */
public final class CacheSlowListenerUtil {

    private CacheSlowListenerUtil(){}

    /**
     * 无
     * @return 监听类列表
     * @since 0.0.9
     */
    public static List<ICacheSlowListener> none() {
        return new ArrayList<>();
    }

    /**
     * 默认实现
     * @return 默认
     * @since 0.0.9
     */
    public static ICacheSlowListener defaults() {
        return new CacheSlowListener();
    }

}
