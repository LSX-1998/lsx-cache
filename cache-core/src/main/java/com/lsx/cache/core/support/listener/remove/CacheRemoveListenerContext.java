package com.lsx.cache.core.support.listener.remove;

import com.lsx.cahe.api.ICacheRemoveListenerContext;

public class CacheRemoveListenerContext<K,V> implements ICacheRemoveListenerContext<K,V> {


    /**
     * key
     */
    private K key;

    /**
     * 值
     */
    private V value;

    /**
     * 删除类型
     */
    private String type;

    /**
     * 新建实例
     * @param <K> key
     * @param <V> value
     * @return 结果
     */
    public static <K,V> CacheRemoveListenerContext<K,V> newInstance() {
        return new CacheRemoveListenerContext<>();
    }




    @Override
    public K getKey() {
        return this.key;
    }

    public CacheRemoveListenerContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    public CacheRemoveListenerContext<K, V> value(V value) {
        this.value = value;
        return this;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public CacheRemoveListenerContext<K, V> type(String type) {
        this.type = type;
        return this;
    }


}
