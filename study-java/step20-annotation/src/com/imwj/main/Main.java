package com.imwj.main;

/**
 * @author wj
 * @create 2023-04-27 14:34
 */
public class Main {

    public static void main(String[] args) {
        // 常见内置注解：@Override、@Deprecated、@SuppressWarnings
        Main main = new Main();
        main.toString();
        main.fun1();
        main.fun2();


    }


    /**
     * 表示覆盖父类的这个方法
     * @return
     */
    @Override
    public String toString(){
        System.out.println("@Override");
        return "null";
    }

    /**
     * 表示该方法后续可能会停用  不建议使用该方法
     */
    @Deprecated
    public void fun1(){
        System.out.println("@Deprecated");
    }

    /**
     * 表示该方法后续可能会停用  不建议使用该方法
     */
    @SuppressWarnings({"all"})
    public void fun2(){
        System.out.println("@SuppressWarnings");
    }
}
