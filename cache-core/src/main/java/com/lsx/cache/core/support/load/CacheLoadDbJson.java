package com.lsx.cache.core.support.load;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cache.core.model.PersistRdbEntry;
import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICacheLoad;
import com.alibaba.fastjson.JSON;
import java.util.List;


public class CacheLoadDbJson<K,V> implements ICacheLoad<K,V> {

    private static final Log log = LogFactory.getLog(CacheLoadDbJson.class);

    /**
     * 文件路径
     */
    private final String dbPath;

    public CacheLoadDbJson(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public void load(ICache<K, V> cache) {
        List<String> lines = FileUtil.readAllLines(dbPath) ;

        log.info("[load] 开始处理 path: {}", dbPath);

        if(CollectionUtil.isEmpty(lines)) {
            log.info("[load] path: {} 文件内容为空，直接返回", dbPath);
            return;
        }

        for(String line : lines) {
            if(StringUtil.isEmpty(line)) {
                continue;
            }

            // 执行
            // 简单的类型还行，复杂的这种反序列化会失败
            PersistRdbEntry<K,V> entry = JSON.parseObject(line, PersistRdbEntry.class);

            K key = entry.getKey();
            V value = entry.getValue();
            Long expire = entry.getExpire();

            cache.put(key, value);
            if(ObjectUtil.isNotNull(expire)) {
                cache.expireAt(key, expire);
            }
        }


    }
}
