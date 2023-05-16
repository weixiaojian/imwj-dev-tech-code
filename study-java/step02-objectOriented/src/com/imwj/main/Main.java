package com.imwj.main;

import com.imwj.entity.Hero;

/**
 * 面向对象：一个勒创建了两个不同的对象，设置了不同的属性值
 * @author wj
 * @create 2023-04-23 10:47
 */
public class Main {

    public static void main(String[] args) {
        Hero hero1 = new Hero();
        hero1.name = "张三";
        hero1.hp = 1000F;
        hero1.armor = 100F;
        hero1.moveSpeed = 330;
        System.out.println(hero1);

        Hero hero2 = new Hero();
        hero2.name = "李四";
        hero2.hp = 2000F;
        hero2.armor = 200F;
        hero2.moveSpeed = 660;
        System.out.println(hero2);
    }

}
