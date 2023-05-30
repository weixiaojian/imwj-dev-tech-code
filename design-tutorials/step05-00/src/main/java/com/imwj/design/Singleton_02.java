package com.imwj.design;

/**
 * 饿汉模式()
 * @author wj
 * @create 2023-05-30 17:47
 */
public class Singleton_02 {

    private static Singleton_02 instance;

    private Singleton_02() {
    }

    public static synchronized Singleton_02 getInstance(){
        if (null != instance) return instance;
        instance = new Singleton_02();
        return instance;
    }

}

