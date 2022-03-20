package com.lsx.cache.core.support.persist;

import com.alibaba.fastjson.JSON;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cache.core.model.PersistRdbEntry;
import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICachePersist;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * 缓存持久化-db-基于 JSON
 */
public class CachePersistDbJson<K,V> extends CachePersistAdaptor<K,V> {



    /**
     * 数据库路径
     */
    private final String dbPath;







    @Override
    public void persist(ICache<K, V> cache) {
        Set<Map.Entry<K,V>> entrySet = cache.entrySet();

        //创建文件
        FileUtil.createFile(dbPath);

        //清空文件
        FileUtil.truncate(dbPath);

        for(Map.Entry<K,V> entry:entrySet){
            K key = entry.getKey();
            Long expireTime = cache.getExpire().expireTime(key) ;
            PersistRdbEntry<K,V> persistEntry = new PersistRdbEntry<>() ;
            persistEntry.setKey(key);
            persistEntry.setValue(entry.getValue());
            persistEntry.setExpire(expireTime);

            String line = JSON.toJSONString(persistEntry) ;
            FileUtil.write(dbPath,line, StandardOpenOption.APPEND);
        }

    }

    @Override
    public long delay() {
        return 5;
    }

    @Override
    public long period() {
        return 5;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }

    public CachePersistDbJson (String dbPath){
         this.dbPath = dbPath ;
    }


}
