package com.imwj.design;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Effective Java作者推荐的枚举单例(线程安全)
 * @author wj
 * @create 2023-05-30 17:57
 */
public enum Singleton_07 {

    INSTANCE;
    public void test(){
        System.out.println("hi~");
    }

}

