package com.imwj.main;

/**
 * @author wj
 * @create 2023-04-24 17:08
 */
public class Main {

    public static void main(String[] args) {
        // 包装类型；Byte、Short、Integer、Long、Float、Double、Boolean、Char
        Integer i1 = 1;
        int i2 = 2;
        System.out.println("自动装箱：" + (i2 = i1));
        System.out.println("自动拆箱：" + (i1 = i2));
        // 类型转换
        String i3 = i1.toString();
        System.out.println("类型转换1：" + i3);
        System.out.println("类型转换2：" + Integer.valueOf(i3));

        // 格式化输出：%s 表示字符串、%d 表示数字、%n 表示换行
        System.out.printf("%d %s %d %s %d %n",1,"+",1,"=",2);
        System.out.format
                ("%d %s %d %s %d %n",1,"+",1,"=",2);

    }


}
