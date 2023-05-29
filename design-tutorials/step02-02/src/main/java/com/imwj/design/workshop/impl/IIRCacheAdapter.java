package com.imwj.design.workshop.impl;

import com.imwj.design.redis.cluster.IIR;
import com.imwj.design.workshop.ICacheAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @create 2023-05-29 11:48
 */
public class IIRCacheAdapter implements ICacheAdapter {

    private IIR iir = new IIR();

    @Override
    public String get(String key) {
        return iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        iir.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        iir.setExpire(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        iir.del(key);
    }

}