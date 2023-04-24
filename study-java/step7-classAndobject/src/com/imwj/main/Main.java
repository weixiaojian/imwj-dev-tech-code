package com.imwj.main;

import com.imwj.entity.Hero;
import com.imwj.entity.Item;
import com.imwj.entity.SeasonEnum;
import com.imwj.entity.Test;

/**
 * @author wj
 * @create 2023-04-24 10:15
 */
public class Main {

    public static void main(String[] args) {
        // 使用一个引用来指向这个对象
        Hero item1 = new Item();
        Hero hero2;
        item1.name = "张三";
        hero2 = item1;
        System.out.println(hero2);
        // 两个引用其实是同一个对象 一个值改变另一个也会跟着改变
        item1.name = "李四";
        System.out.println(hero2);

        // 继承：子类会拥有父类所有的公共属性和方法
        Item item = new Item();
        item.name = "王五";
        item.damage = 100;
        System.out.println(item.toString());

        // toString方法重载：方法名一样参数不一样，重写：方法名和参数都一致
        System.out.println(item.toString("test"));

        // 有参构造
        Item item2 = new Item(1, 2, "赵六");
        System.out.println(item2);

        // static静态变量可以直接赋值和使用
        Item.age  = 1;
        System.out.println(Item.age);

        // 单例模式
        System.out.println(Test.getTest1() == Test.getTest1());
        System.out.println(Test.getTest2() == Test.getTest2());

        // 枚举的使用
        SeasonEnum season = SeasonEnum.SPRING;
        switch (season) {
            case SPRING:
                System.out.println("春天");
                break;
            case SUMMER:
                System.out.println("夏天");
                break;
            case AUTUMN:
                System.out.println("秋天");
                break;
            case WINTER:
                System.out.println("冬天");
                break;
        }
    }
}
