package com.lsx.cache.core.support.evict;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cache.core.exception.CacheRuntimeException;
import com.lsx.cache.core.model.CacheEntry;
import com.lsx.cache.core.model.FreqNode;
import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheEntry;
import com.lsx.cahe.api.ICacheEvictContext;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class CacheEvictLfu<K,V> extends AbstractCacheEvict<K,V> {


    private static final Log log = LogFactory.getLog(CacheEvictLfu.class);

    /**
     * key 映射信息
     */
    private final Map<K, FreqNode<K,V>> keyMap ;


    /**
     * 频率 map
     */
    private final Map<Integer, LinkedHashSet<FreqNode<K,V>>> freqMap;



    public CacheEvictLfu() {
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFreq = 1;
    }



    /**
     *
     * 最小频率
     * @since 0.0.14
     */
    private int minFreq;




    /**
     * 加入到频率 MAP
     * @param frequency 频率
     * @param freqNode 节点
     */
    private void addToFreqMap(final int frequency, FreqNode<K,V> freqNode) {
        LinkedHashSet<FreqNode<K,V>> set = freqMap.get(frequency) ;
        if(set==null){
            set = new LinkedHashSet<>() ;
        }
        set.add(freqNode) ;
        freqMap.put(frequency,set);

    }



    /**
     * 获取最小频率的 节点
     *
     * @return 结果
     * @since 0.0.14
     */
    private FreqNode<K, V> getMinFreqNode() {
         LinkedHashSet<FreqNode<K,V>> set = freqMap.get(minFreq) ;
         if(!CollectionUtil.isEmpty(set)){
             return set.iterator().next();
         }

        throw new CacheRuntimeException("未发现最小频率的 Key") ;
    }





    /**
     * 更新元素，更新 minFreq 信息
     * @param key 元素
     * @since 0.0.14
     */
    @Override
    public void updateKey(K key) {
        FreqNode<K, V> freqNode = keyMap.get(key);

        //1. 已经存在
        if(ObjectUtil.isEmpty(freqNode)){
           int frequency =  freqNode.frequency() ;
           LinkedHashSet<FreqNode<K,V>> oldSet = freqMap.get(frequency) ;
           oldSet.remove(freqNode);

            //1.2 更新最小数据频率
           if(minFreq==frequency&&oldSet.isEmpty()){
               minFreq++ ;
               log.debug("minFreq 增加为：{}", minFreq);
           }

            //1.3 更新频率信息
           frequency++ ;
           freqNode.frequency(frequency);

            //1.4 放入新的集合
           this.addToFreqMap(frequency,freqNode);
        }else{

            //2. 不存在
            //2.1 构建新的元素
            freqNode = new FreqNode<K,V>(key) ;

            //2.2 固定放入到频率为1的列表中
            this.addToFreqMap(1,freqNode);

            //2.3 更新 minFreq 信息
            this.minFreq =1 ;

            //2.4 添加到 keyMap
            this.keyMap.put(key,freqNode) ;
        }
    }

    @Override
    public void removeKey(K key) {
        FreqNode<K, V> freqNode = this.keyMap.remove(key);
        int frequency = freqNode.frequency();


        //1. 根据 key 获取频率
        LinkedHashSet<FreqNode<K, V>> set = freqMap.get(frequency);

        //2. 移除频率中对应的节点
        set.remove(freqNode) ;
        log.debug("freq={} 移除元素节点：{}", frequency, freqNode);

        //3. 更新 minFreq
        if(frequency==minFreq&&set.isEmpty()){
            minFreq--;
            log.debug("minFreq 降低为：{}", minFreq);
        }



    }

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICacheEntry<K,V> result = null ;

        ICache<K, V> cache = context.cache();
        if(cache.size()>=context.size()){
            FreqNode<K, V> minFreqNode = this.getMinFreqNode();
            K evictKey = minFreqNode.key();
            V evictValue = cache.remove(evictKey);
            log.debug("淘汰最小频率信息, key: {}, value: {}, freq: {}",
                    evictKey, evictValue, minFreqNode.frequency());

            result = new CacheEntry<>(evictKey, evictValue);
        }
        return  result ;
    }


}
