package com.lsx.cache.core.util;

import com.lsx.cache.core.support.load.CacheLoadAof;
import com.lsx.cache.core.support.load.CacheLoadDbJson;
import com.lsx.cache.core.support.load.CacheLoadNone;
import com.lsx.cahe.api.ICacheLoad;
/**
 *
 * 加载策略工具类
 * @author binbin.hou
 * @since 0.0.7
 */
public final class CacheLoadUtil {
    private CacheLoadUtil(){}


    /**
     * 无加载
     * @param <K> key
     * @param <V> value
     * @return 值
     * @since 0.0.7
     */
    public static <K,V> ICacheLoad<K,V> none() {
        return new CacheLoadNone<>();
    }




    /**
     * 文件 JSON
     * @param dbPath 文件路径
     * @param <K> key
     * @param <V> value
     * @return 值
     * @since 0.0.8
     */
    public static <K,V> ICacheLoad<K,V> dbJson(final String dbPath) {
        return new CacheLoadDbJson<>(dbPath);
    }

    /**
     * AOF 文件加载模式
     * @param dbPath 文件路径
     * @param <K> key
     * @param <V> value
     * @return 值
     * @since 0.0.10
     */
    public static <K,V> ICacheLoad<K,V> aof(final String dbPath) {
        return new CacheLoadAof<>(dbPath);
    }
}
