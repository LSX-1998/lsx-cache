package com.lsx.cache.core.util;


import com.lsx.cache.core.support.evict.CacheEvictFifo;
import com.lsx.cache.core.support.evict.CacheEvictLfu;
import com.lsx.cache.core.support.evict.CacheEvictNone;
import com.lsx.cahe.api.ICacheEvict;

/**
 * 丢弃策略
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public final class CacheEvictUtil {

    private CacheEvictUtil(){}

    /**
     * 无策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.2
     */

    public static <K, V> ICacheEvict<K, V> none() {
        return new CacheEvictNone<>();
    }
    /**
     * 先进先出
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.2
     */
    public static <K, V> ICacheEvict<K, V> fifo() {
        return new CacheEvictFifo<>();
    }

    /**
     * LRU 驱除策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.11
     */


    /**
     * LRU 驱除策略
     *
     * 基于双向链表 + map 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.12
     */



    /**
     * LRU 驱除策略
     *
     * 基于LinkedHashMap
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.12
     */


    /**
     * LRU 驱除策略
     *
     * 基于 2Q 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.13
     */


    /**
     * LRU 驱除策略
     *
     * 基于 LRU-2 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.13
     */


    /**
     * LFU 驱除策略
     *
     * 基于 LFU 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.14
     */
    public static <K, V> ICacheEvict<K, V> lfu() {
        return new CacheEvictLfu<>();
    }


    /**
     * 时钟算法
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 0.0.15
     */


}
