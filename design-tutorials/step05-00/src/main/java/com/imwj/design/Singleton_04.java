package com.imwj.design;

/**
 * 使用类的内部类(线程安全)
 * @author wj
 * @create 2023-05-30 17:57
 */
public class Singleton_04 {


    private static class SingletonHolder {
        private static Singleton_04 instance = new Singleton_04();
    }

    private Singleton_04() {
    }

    public static Singleton_04 getInstance() {
        return SingletonHolder.instance;
    }

}
