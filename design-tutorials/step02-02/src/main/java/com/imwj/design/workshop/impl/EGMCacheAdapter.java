package com.imwj.design.workshop.impl;

import com.imwj.design.redis.cluster.EGM;
import com.imwj.design.workshop.ICacheAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @create 2023-05-29 11:48
 */
public class EGMCacheAdapter implements ICacheAdapter {


    private EGM egm = new EGM();

    public String get(String key) {
        return egm.gain(key);
    }

    public void set(String key, String value) {
        egm.set(key, value);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        egm.setEx(key, value, timeout, timeUnit);
    }

    public void del(String key) {
        egm.delete(key);
    }
}
