package com.lsx.cache.core.bs;


import com.github.houbb.heaven.util.common.ArgUtil;
import com.lsx.cache.core.core.Cache;
import com.lsx.cache.core.support.proxy.CacheProxy;
import com.lsx.cache.core.util.*;
import com.lsx.cahe.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存引导类
 */
public final class CacheBs<K,V> {

    private CacheBs(){}

    /**
     * 创建对象实例
     * @param <K> key
     * @param <V> value
     * @return this
     */
    public static <K,V> CacheBs<K,V> newInstance() {
        return new CacheBs<>();
    }

    /**
     * map 实现
     * @since 0.0.2
     */
    private Map<K,V> map = new HashMap<>();

    /**
     * 大小限制
     * @since 0.0.2
     */
    private int size = Integer.MAX_VALUE;

    /**
     * 驱除策略
     * @since 0.0.2
     */
    private ICacheEvict<K,V> evict = CacheEvictUtil.fifo();

    /**
     * 删除监听类
     * @since 0.0.6
     */
    private final List<ICacheRemoveListener<K,V>> removeListeners = CacheRemoveListenerUtil.defaults();

    /**
     * 慢操作监听类
     * @since 0.0.9
     */
    private final List<ICacheSlowListener> slowListeners = CacheSlowListenerUtil.none();

    /**
     * 加载策略
     * @since 0.0.7
     */
    private ICacheLoad<K,V> load = CacheLoadUtil.none();

    /**
     * 持久化实现策略
     * @since 0.0.8
     */
    private ICachePersist<K,V> persist = CachePersistUtil.none();

    /**
     * map 实现
     * @param map map
     * @return this
     * @since 0.0.2
     */
    public CacheBs<K, V> setMap(Map<K, V> map) {
        ArgUtil.notNull(map, "map");

        this.map = map;
        return this;
    }

    /**
     * 设置 size 信息
     * @param size size
     * @return this
     * @since 0.0.2
     */
    public CacheBs<K, V> setSize(int size) {
        ArgUtil.notNegative(size, "size");

        this.size = size;
        return this;
    }

    /**
     * 设置驱除策略
     * @param evict 驱除策略
     * @return this
     * @since 0.0.2
     */
    public CacheBs<K, V> setEvict(ICacheEvict<K, V> evict) {
        ArgUtil.notNull(evict, "evict");

        this.evict = evict;
        return this;
    }

    /**
     * 设置加载
     * @param load 加载
     * @return this
     * @since 0.0.7
     */
    public CacheBs<K, V> setLoad(ICacheLoad<K, V> load) {
        ArgUtil.notNull(load, "load");

        this.load = load;
        return this;
    }

    /**
     * 添加删除监听器
     * @param removeListener 监听器
     * @return this
     * @since 0.0.6
     */
    public CacheBs<K, V> addRemoveListener(ICacheRemoveListener<K,V> removeListener) {
        ArgUtil.notNull(removeListener, "removeListener");

        this.removeListeners.add(removeListener);
        return this;
    }

    /**
     * 添加慢日志监听器
     * @param slowListener 监听器
     * @return this
     * @since 0.0.9
     */
    public CacheBs<K, V> addSlowListener(ICacheSlowListener slowListener) {
        ArgUtil.notNull(slowListener, "slowListener");

        this.slowListeners.add(slowListener);
        return this;
    }

    /**
     * 设置持久化策略
     * @param persist 持久化
     * @return this
     * @since 0.0.8
     */
    public CacheBs<K, V> setPersist(ICachePersist<K, V> persist) {
        this.persist = persist;
        return this;
    }

    /**
     * 构建缓存信息
     * @return 缓存信息
     * @since 0.0.2
     */
    public ICache<K,V> build() {
        Cache<K,V> cache = new Cache<>();
        cache.setMap(map);
        cache.setEvict(evict);
        cache.setSizeLimit(size);
        cache.setRemoveListeners(removeListeners);
        cache.setLoad(load);
        cache.setPersist(persist);
        cache.setSlowListeners(slowListeners);

        // 初始化
        cache.init();
        return CacheProxy.getProxy(cache);
    }

}
