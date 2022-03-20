package com.lsx.cahe.api;

/**
 * 删除监听器上下文
 *
 * （1）耗时统计
 * （2）监听器
 *
 * @param <K> key
 * @param <V> value
 */
public interface ICacheRemoveListenerContext<K,V> {
    /**
     * 清空的 key
     * @return key
     */
    K getKey();

    /**
     * 值
     * @return 值
     */
    V getValue();

    /**
     * 删除类型
     * @return 类型
     */
    String getType();
}
