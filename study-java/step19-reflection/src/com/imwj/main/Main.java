package com.imwj.main;

import com.imwj.entity.Hero;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wj
 * @create 2023-04-27 11:38
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // 获取类对象的三种方式
        Class<Hero> clazz1 = (Class<Hero>) Class.forName("com.imwj.entity.Hero");
        Class<Hero> clazz2 = Hero.class;
        Class<Hero> clazz3 = (Class<Hero>) new Hero().getClass();
        System.out.println(clazz1);
        System.out.println(clazz2);
        System.out.println(clazz3);

        // 反射：通过类对象创建对象(无参构造)
        Constructor<Hero> constructor = clazz1.getConstructor();
        Hero hero = constructor.newInstance();
        hero.name = "张张";
        System.out.println(hero);

        // 反射：修改属性值
        Field f1 = clazz1.getDeclaredField("name");
        f1.set(hero,"李四");
        System.out.println(hero);

        // 反射：调用方法
        Method method = clazz1.getMethod("test", String.class);
        method.invoke(hero, "王五");

    }

}
