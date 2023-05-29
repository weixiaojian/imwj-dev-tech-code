package com.imwj.design.workshop;

import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @create 2023-05-29 11:46
 */
public interface ICacheAdapter {


    String get(final String key);

    void set(String key, String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}
