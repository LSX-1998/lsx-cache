package com.lsx.cache.core.support.expire;

import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheExpire;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CacheExpireSort<K,V> implements ICacheExpire<K,V> {

    /**
     * 单次清空的数量限制
     */
    private static final int LIMIT = 100;


    private final Map<Long, List<K>> sortMap = new TreeMap<>((o1,o2)-> (int) (o1-o2)) ;

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

    public CacheExpireSort(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 1, 1, TimeUnit.SECONDS);
    }


    /**
     * 定时执行任务
     */
    private class ExpireThread implements Runnable {
        @Override
        public void run() {
            //1.判断是否为空
            if(sortMap.isEmpty()) {
                return;
            }

            //2. 获取 key 进行处理
            int count = 0;
            for(Map.Entry<Long, List<K>> entry : sortMap.entrySet()) {
                final Long expireAt = entry.getKey();
                List<K> expireKeys = entry.getValue();

                // 判断队列是否为空
                if(expireKeys.isEmpty()) {
                    sortMap.remove(expireAt);
                    continue;
                }
                if(count >= LIMIT) {
                    return;
                }

                // 删除的逻辑处理
                long currentTime = System.currentTimeMillis();
                if(currentTime >= expireAt) {
                    Iterator<K> iterator = expireKeys.iterator();
                    while (iterator.hasNext()) {
                        K key = iterator.next();
                        // 先移除本身
                        iterator.remove();
                        expireMap.remove(key);

                        // 再移除缓存，后续可以通过惰性删除做补偿
                        cache.remove(key);

                        count++;
                    }
                } else {
                    // 直接跳过，没有过期的信息
                    return;
                }
            }
        }
    }



    @Override
    public void expire(K key, long expireAt) {
        List<K> keys = sortMap.get(expireAt);
        if(keys == null) {
            keys = new ArrayList<>();
        }
        keys.add(key);

        // 设置对应的信息
        sortMap.put(expireAt, keys);
        expireMap.put(key, expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if(keyList.isEmpty()||keyList.size()==0) {
            return;
        }

        // 这样维护两套的代价太大，后续优化，暂时不用。
        // 判断大小，小的作为外循环
        final int expireSize = expireMap.size();
        if(expireSize <= keyList.size()) {
            // 一般过期的数量都是较少的
            for(Map.Entry<K,Long> entry : expireMap.entrySet()) {
                K key = entry.getKey();

                // 这里直接执行过期处理，不再判断是否存在于集合中。
                // 因为基于集合的判断，时间复杂度为 O(n)
                this.removeExpireKey(key);
            }
        } else {
            for(K key : keyList) {
                this.removeExpireKey(key);
            }
        }
    }

    @Override
    public Long expireTime(Object key) {
        return null;
    }


    /**
     * 移除过期信息
     * @param key key
     */
    private void removeExpireKey(final K key) {
        Long expireTime = expireMap.get(key);
        if(expireTime != null) {
            final long currentTime = System.currentTimeMillis();
            if(currentTime >= expireTime) {
                expireMap.remove(key);

                List<K> expireKeys = sortMap.get(expireTime);
                expireKeys.remove(key);
                sortMap.put(expireTime, expireKeys);
            }
        }
    }

}
