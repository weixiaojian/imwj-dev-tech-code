package com.imwj.design;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例模式：静态类
 * @author wj
 * @create 2023-05-30 17:42
 */
public class Singleton_00 {

    private static Map<String, String> cache = new ConcurrentHashMap<String, String>();

}
