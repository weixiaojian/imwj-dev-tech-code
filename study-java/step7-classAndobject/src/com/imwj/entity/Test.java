package com.imwj.entity;

/**
 * @author wj
 * @create 2023-04-24 15:05
 */
public class Test {

    /**
     * 私有化构造方法，用户无法直接new
     */
    private Test(){

    }

    /**
     * 饿汉模式
     * @return
     */
    private static Test test1 = new Test();
    public static Test getTest1(){
        return test1;
    }

    /**
     * 懒汉模式
     */
    private static Test test2;
    public static Test getTest2(){
        if(test2 == null){
            test2 = new Test();
        }
        return test2;
    }
}
