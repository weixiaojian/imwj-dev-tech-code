package com.imwj.design.application;

import java.util.concurrent.TimeUnit;

/**
 * 缓存服务类
 * @author wj
 * @create 2023-05-29 11:16
 */
public interface CacheService {


    String get(final String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);

}
