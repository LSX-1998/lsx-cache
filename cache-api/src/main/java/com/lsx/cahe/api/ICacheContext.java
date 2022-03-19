package com.lsx.cahe.api;

import java.util.Map;
/**
 * 缓存上下文
 */
public interface ICacheContext<K,V> {
    /**
     * map 信息
     * @return map
     */
    Map<K, V> map();

    /**
     * 大小限制
     * @return 大小限制
     */
    int size();

    /**
     * 驱除策略
     * @return 策略
     * @since 0.0.2
     */
    ICacheEvict<K,V> cacheEvict();
}
