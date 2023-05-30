package com.imwj.design;

/**
 * 饿汉模式(线程安全)
 * @author wj
 * @create 2023-05-30 17:51
 */
public class Singleton_03 {

    private static Singleton_03 instance = new Singleton_03();

    private Singleton_03() {
    }

    public static Singleton_03 getInstance() {
        return instance;
    }

}
