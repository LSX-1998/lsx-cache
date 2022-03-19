package com.lsx.cache.core.util;


import com.lsx.cache.core.support.Interceptor.aof.CacheInterceptorAof;
import com.lsx.cache.core.support.Interceptor.common.CacheInterceptorCost;
import com.lsx.cache.core.support.Interceptor.evict.CacheInterceptorEvict;
import com.lsx.cache.core.support.Interceptor.refresh.CacheInterceptorRefresh;
import com.lsx.cahe.api.ICacheInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存拦截器工具类
 * @author binbin.hou
 * @since 0.0.5
 */
public final class CacheInterceptorUtil {

    /**
     * 默认通用
     * @return 结果
     * @since 0.0.5
     */
    @SuppressWarnings("all")
    public static List<ICacheInterceptor> defaultCommonList() {
        List<ICacheInterceptor> list = new ArrayList<>();
        list.add(new CacheInterceptorCost());
        return list;
    }

    /**
     * 默认刷新
     * @return 结果
     * @since 0.0.5
     */
    @SuppressWarnings("all")
    public static List<ICacheInterceptor> defaultRefreshList() {
        List<ICacheInterceptor> list = new ArrayList<>();
        list.add(new CacheInterceptorRefresh());
        return list;
    }

    /**
     * AOF 模式
     * @return 结果
     * @since 0.0.10
     */
    @SuppressWarnings("all")
    public static ICacheInterceptor aof() {
        return new CacheInterceptorAof();
    }

    /**
     * 驱除策略拦截器
     * @return 结果
     * @since 0.0.11
     */
    @SuppressWarnings("all")
    public static ICacheInterceptor evict() {
        return new CacheInterceptorEvict();
    }

}
