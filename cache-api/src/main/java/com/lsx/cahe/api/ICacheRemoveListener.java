package com.lsx.cahe.api;

public interface ICacheRemoveListener<K,V> {
    /**
     * 监听
     * @param context 上下文
     */
    void listen(final ICacheRemoveListenerContext<K,V> context);
}
