package com.lsx.cache.core.support.listener.slow;

import com.lsx.cahe.api.ICacheSlowListenerContext;

public class CacheSlowListenerContext implements ICacheSlowListenerContext {

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数信息
     */
    private Object[] params;

    /**
     * 方法结果
     */
    private Object result;

    /**
     * 开始时间
     */
    private long startTimeMills;

    /**
     * 结束时间
     */
    private long endTimeMills;

    /**
     * 消耗时间
     */
    private long costTimeMills;

    /**
     * @return 实例
     */
    public static CacheSlowListenerContext newInstance() {
        return new CacheSlowListenerContext();
    }

    @Override
    public String getMethodName() {
        return methodName ;
    }

    public CacheSlowListenerContext methodName(String methodName){
        this.methodName = methodName ;
        return this ;
    }

    @Override
    public Object[] getParams() {
        return params;
    }

    public CacheSlowListenerContext params(Object[] params){
        this.params = params ;
        return this ;
    }



    @Override
    public Object getResult() {
        return result;
    }

    public CacheSlowListenerContext result(Object result){
        this.result = result ;
        return this ;
    }

    @Override
    public long startTimeMills() {
        return startTimeMills;
    }

    public CacheSlowListenerContext startTimeMIlls(long startTimeMills ){
         this.startTimeMills = startTimeMills ;
         return this ;
    }

    @Override
    public long endTimeMills() {
        return endTimeMills;
    }

    public CacheSlowListenerContext endTimeMills(long endTimeMills){
        this.endTimeMills = endTimeMills ;
        return this ;
    }

    @Override
    public long costTimeMills() {
        return costTimeMills;
    }

    public CacheSlowListenerContext costTimeMills(long costTimeMills){
        this.costTimeMills = costTimeMills ;
        return this ;
    }
}
