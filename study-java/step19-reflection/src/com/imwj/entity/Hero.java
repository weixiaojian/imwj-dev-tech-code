package com.imwj.entity;

/**
 * @author wj
 * @create 2023-04-27 11:38
 */
public class Hero {

    static {
        System.out.println("静态初始化 copyright");
    }
    {
        System.out.println("普通代码块初始化 copyright");
    }

    public Hero() {
        System.out.println("构造方法初始化 copyright");
    }

    public String name;

    public Integer age;

    public void test(String name){
        System.out.println("test：" + name);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
