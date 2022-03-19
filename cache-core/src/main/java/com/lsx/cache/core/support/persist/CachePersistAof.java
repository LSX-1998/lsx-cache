package com.lsx.cache.core.support.persist;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.lsx.cahe.api.ICache;
import com.lsx.cahe.api.ICachePersist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * 缓存持久化-AOF 持久化模式
 */
public class CachePersistAof<K,V> extends CachePersistAdaptor<K,V> {





    private static final Log log = LogFactory.getLog(CachePersistAof.class);


    /**
     * 缓存列表
     * @since 0.0.10
     */
    private final List<String> bufferList = new ArrayList<>();


    /**
     * 数据持久化路径
     * @since 0.0.10
     */
    private final String dbPath ;



    public CachePersistAof(String dbPath){
        this.dbPath = dbPath ;
    }



    @Override
    public void persist(ICache<K, V> cache) {
      log.info("开始 AOF 持久化到文件");
        // 1. 创建文件
        if(!FileUtil.exists(dbPath)) {
            FileUtil.createFile(dbPath);
        }
        FileUtil.append(dbPath,bufferList);
        // 3. 清空 buffer 列表
        bufferList.clear();
        log.info("完成 AOF 持久化到文件");
    }

    @Override
    public long delay() {
        return 1;
    }

    @Override
    public long period() {
        return 1;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
    }

    /**
     * 添加文件内容到 buffer 列表中
     * @param json json 信息
     * @since 0.0.10
     */
    public void append(final String json) {
        if(StringUtil.isNotEmpty(json)) {
            bufferList.add(json);
        }
    }
}
