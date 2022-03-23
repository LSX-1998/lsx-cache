package com.lsx.cache.core.core;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.lsx.cache.core.constant.enums.CacheRemoveType;
import com.lsx.cache.core.exception.CacheRuntimeException;
import com.lsx.cache.core.support.evict.CacheEvictContext;
import com.lsx.cache.core.support.expire.CacheExpire;
import com.lsx.cache.core.support.listener.remove.CacheRemoveListenerContext;
import com.lsx.cache.core.support.persist.InnerCachePersist;
import com.lsx.cache.core.support.proxy.CacheProxy;
import com.lsx.cahe.annotation.CacheInterceptor;
import com.lsx.cahe.api.*;

import java.util.*;

public class Cache<K,V> implements ICache<K,V> {
    /**
     * map 信息
     */
    private Map<K,V> map;

    /**
     * 大小限制
     */
    private int sizeLimit;

    /**
     * 驱除策略
     */
    private ICacheEvict<K,V> evict;

    /**
     * 过期策略
     * 暂时不做暴露
     */
    private ICacheExpire<K,V> expire;

    /**
     * 删除监听类
     */
    private List<ICacheRemoveListener<K,V>> removeListeners;

    /**
     * 慢日志监听类
     */
    private List<ICacheSlowListener> slowListeners;

    /**
     * 加载类
     */
    private ICacheLoad<K,V> load;

    /**
     * 持久化
     */
    private ICachePersist<K,V> persist;





    /**
     * 初始化
     */
    public void init() {
        this.expire = new CacheExpire<>(this);
        this.load.load(this);

        // 初始化持久化
        if(this.persist != null) {
            new InnerCachePersist<>(this, persist);
        }
    }


    /**
     * 设置 map 实现
     * @param map 实现
     * @return this
     */
    public Cache<K, V> setMap(Map<K, V> map) {
        this.map = map;
        return this;
    }

    /**
     * 设置大小限制
     * @param sizeLimit 大小限制
     * @return this
     */
    public Cache<K, V> setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }



    /**
     * 设置驱除策略
     * @param cacheEvict 驱除策略
     * @return this
     */
    public Cache<K, V> setEvict(ICacheEvict<K, V> cacheEvict) {
        this.evict = cacheEvict;
        return this;
    }

    /**
     * 是否已经达到大小最大的限制
     * @return 是否限制
     */
    private boolean isSizeLimit() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    /**
     * 设置持久化策略
     * @param persist 持久化
     */
    public void setPersist(ICachePersist<K, V> persist) {
        this.persist = persist;
    }


    /**
     * 设置删除监听器
     * @param removeListeners 删除监听器
     */
    public Cache<K, V> setRemoveListeners(List<ICacheRemoveListener<K, V>> removeListeners) {
        this.removeListeners = removeListeners;
        return this;
    }


    /**
     * 设置慢日志监听器
     * @param slowListeners 慢日志监听器
     */
    public Cache<K, V> setSlowListeners(List<ICacheSlowListener> slowListeners) {
        this.slowListeners = slowListeners;
        return this;
    }





    /**
     * 设置缓存加载器
     * @param load 缓存加载器
     */
    public Cache<K, V> setLoad(ICacheLoad<K, V> load) {
        this.load = load;
        return this;
    }










    @Override
    @CacheInterceptor(aof = true, evict = true)
    public V put(K key, V value) {
        //1.1 尝试驱除
        CacheEvictContext<K,V> context = new CacheEvictContext<K, V>();
        context.key(key).size(sizeLimit).cache(this);

        ICacheEntry<K, V> entry = evict.evict(context);

        //System.out.println("lsx");
        // 添加拦截器调用
        if(ObjectUtil.isNotNull(entry)) {
            // 执行淘汰监听器
            ICacheRemoveListenerContext<K,V> removeListenerContext = CacheRemoveListenerContext.<K,V>newInstance().key(entry.getKey())
                    .value(entry.getValue())
                    .type(CacheRemoveType.EVICT.code());
            for(ICacheRemoveListener<K,V> listener : context.getCache().getRemoveListeners()) {
                listener.listen(removeListenerContext);
            }
        }

        //2. 判断驱除后的信息
        if(isSizeLimit()) {
            throw new CacheRuntimeException("当前队列已满，数据添加失败！");
        }

        //3. 执行添加
        return map.put(key, value);



    }

    @Override
    @CacheInterceptor(refresh = true)
    public int size() {
        return map.size();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    @CacheInterceptor(refresh = true,evict = true)
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    @CacheInterceptor(refresh = true)
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    @CacheInterceptor(evict = true)
    public V get(Object key) {
        K genericKey = (K) key ;
        this.expire.refreshExpire(Collections.singletonList(genericKey));
        return map.get(key);
    }

    @Override
    @CacheInterceptor(aof = true ,evict = true)
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    @CacheInterceptor(aof = true)
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    @CacheInterceptor(aof = true,refresh = true)
    public void clear() {
       map.clear();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Collection<V> values() {
        return map.values();
    }

    @Override
    @CacheInterceptor(refresh = true)
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }


    /**
     * 设置过期时间
     * @param key         key
     * @param timeInMills 毫秒时间之后过期
     * @return this
     */
    @Override
    @CacheInterceptor
    public ICache<K, V> expire(K key, long timeInMills) {

        long expireTime = System.currentTimeMillis()+timeInMills ;

        // 使用代理调用
        Cache<K,V> cachePoxy = (Cache<K, V>) CacheProxy.getProxy(this);
        return cachePoxy.expireAt(key,expireTime) ;
    }


    /**
     * 指定过期信息
     * @param key key
     * @param timeInMills 时间戳
     * @return this
     */
    @Override
    @CacheInterceptor(aof = true)
    public ICache<K, V> expireAt(K key, long timeInMills) {
         this.expire.expire(key,timeInMills);
         return this ;
    }

    @Override
    @CacheInterceptor
    public ICacheExpire<K, V> getExpire() {
        return this.expire ;
    }

    @Override
    @CacheInterceptor
    public List<ICacheRemoveListener<K, V>> getRemoveListeners() {
        return this.removeListeners;
    }

    @Override
    public List<ICacheSlowListener> getSlowListeners() {
        return this.slowListeners;
    }

    @Override
    public ICacheLoad<K, V> getLoad() {
        return this.load;
    }

    @Override
    public ICachePersist<K, V> getPersist() {
        return this.persist;
    }

    @Override
    public ICacheEvict<K, V> getEvict() {
        return this.evict;
    }
}
