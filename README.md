# lsx-cache
## 项目简介
 一个用 Java 实现的可拓展的本地缓存系统，可用于多级缓存开发

## 创作目的

- 学习Redis的过程中，想用java实现一个缓存
- 尝试以后用于多级缓存开发
- 学习缓存框架的实现原理

## 支持特性

- 支持数据缓存、缓存失效时间。
- 支持自定义 map 实现策略。
- 支持 expire 过期特性（定期过期和惰性过期）
- 支持自定义的Key 淘汰策略（Lru和Lfu以及Fifo）
- 实现了缓存的初始化和持久化。(RDB以及AOF)
- 实现了自定义的删除监听器以及慢日志监听器。


## 项目所需要的环境

- java JDK1.8以上
- maven3.x以上

## 入门使用
```
<dependency>
    <groupId>com.lsx.project</groupId>
    <artifactId>lsx-cache</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 简单测试
```
 /**
     * 大小指定测试
     * @since 0.0.2
     */
    @Test
    public void helloTest() {
        ICache<String, String> cache = CacheBs.<String, String>newInstance()
                .setSize(1)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");
        cache.put("5","5");
        cache.put("hello,lsx","hello,lsx");

        Assert.assertEquals(1, cache.size());
        System.out.println(cache.keySet());
    }
```
默认使用fifo策略，设置缓存数量为1

输出
```[hello,lsx]```

## 后续待优化的点
- 保证线程安全
- 尝试像Redis一样使用混合持久化机制
- 数据统计
- 



