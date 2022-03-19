package com.lsx.cache.core.support.expire;



import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheExpire;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缓存过期-普通策略
 *
 * @param <K> key
 * @param <V> value
 */
public class CacheExpire<K,V> implements ICacheExpire<K,V> {

    /**
     * 单次清空的数量限制
     */
    private static final int LIMIT = 100;

    /**
     * 过期 map
     *
     * 空间换时间
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     */
    private final ICache<K,V> cache;

    /**
     * 线程执行类
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public CacheExpire(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpiredThread(), 100, 100, TimeUnit.MILLISECONDS);
    }


    private class ExpiredThread implements Runnable{

        @Override
        public void run() {
           //判断是否为空
          if(expireMap==null||expireMap.size()==0){
              return ;
          }

          //获取Key进行处理
            int count = 0;
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                if(count >= LIMIT) {
                    return;
                }

                expireKey(entry.getKey(), entry.getValue());
                count++;
            }

        }
    }


    @Override
    public void expire(K key, long expireAt) {
         expireMap.put(key,expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
         if(keyList==null||keyList.size()==0){
             return ;
         }
        // 判断大小，小的作为外循环。一般都是过期的 keys 比较小。
        if(keyList.size() <= expireMap.size()) {
            for(K key : keyList) {
                Long expireAt = expireMap.get(key);
                expireKey(key, expireAt);
            }
        } else {
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                this.expireKey(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Long expireTime(K key) {
        return null;
    }

    /**
     * 过期处理 key
     * @param key key
     * @param expireAt 过期时间
     * @since 0.0.3
     */
    private void expireKey(final K key, final Long expireAt) {
        if(expireAt == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt) {
            expireMap.remove(key);
            // 再移除缓存，后续可以通过惰性删除做补偿
            V removeValue = cache.remove(key);


        }
    }

}
