package com.imwj.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wj
 * @create 2023-04-26 15:36
 */
public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        // 遍历输出
        list.forEach(d -> System.out.println(d));
        System.out.println("------");

        // 过滤
        list.stream().filter(d -> d>2).forEach(d -> System.out.println(d));
        System.out.println("------");

        // 去重
        list.add(3);
        list.add(3);
        list.stream().distinct().forEach(d -> System.out.println(d));
        System.out.println("------");

        // 排序
        list.stream().sorted((d1, d2) -> d2 > d1?1:-1).forEach(d -> System.out.println(d));
        System.out.println("------");

        // 取最小
        Integer integer = list.stream().min((d1, d2) -> d1 - d2).get();
        System.out.println(integer);
    }

}
