package com.imwj.design;

import java.util.concurrent.TimeUnit;

/**
 * 缓存服务类
 * @author wj
 * @create 2023-05-29 11:34
 */
public interface CacheService {


    String get(final String key, int redisType);

    void set(String key, String value, int redisType);

    void set(String key, String value, long timeout, TimeUnit timeUnit, int redisType);

    void del(String key, int redisType);
}
