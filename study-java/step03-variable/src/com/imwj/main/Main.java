package com.imwj.main;

/**
 * 变量：用于存储数据值，运行时是存在于内存之中
 * @author wj
 * @create 2023-04-23 10:59
 */
public class Main {

    public static void main(String[] args) {
        // 数据类型 变量名 = 变量值
        Integer year = 2023;
        System.out.println(year);

        // 基本类型8中：byte、short、int、long、float、double、boolean、char
        byte a1 = 1;
        short a2 = 2;
        int a3 = 3;
        long a4 = 4L;
        float a5 = 5F;
        double a6 = 6D;
        boolean a7 = true;
        char a8 = '8';
        System.out.println("a1=" + a1 + ", a2=" + a2 + ", a3=" + a3 + ", a4=" + a4 + ", a5=" + a5 + ", a6=" + a6
                + ", a7=" + a7 + ", a8=" + a8);

        // 16进制
        int hexVal = 0x1a;
        // 8进制
        int oxVal = 032;
        // 2进制
        int binVal = 0b11010;
        System.out.println(hexVal + "-" + oxVal + "-" + binVal);

        // 类型转换
        char c = 'A';
        short s = 80;
        c = (char) s;
        System.out.println(c);

        // final修饰
        final int i = 5;
        // i = 10; // 编译错误 final修饰后无法再次赋值
        System.out.println(i);
    }



}
