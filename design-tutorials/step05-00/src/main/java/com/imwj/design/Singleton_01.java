package com.imwj.design;

/**
 * 懒汉模式(线程安全)
 *
 * @author wj
 * @create 2023-05-30 17:43
 */
public class Singleton_01 {

    private static Singleton_01 singleton_01;

    private Singleton_01() {

    }

    public static Singleton_01 getSingleton_01() {
        if (singleton_01 == null) {
            singleton_01 = new Singleton_01();
        }
        return singleton_01;
    }

}
