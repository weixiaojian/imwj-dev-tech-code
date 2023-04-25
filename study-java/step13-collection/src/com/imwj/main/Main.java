package com.imwj.main;

import java.util.*;

/**
 * @author wj
 * @create 2023-04-25 16:51
 */
public class Main {

    public static void main(String[] args) {
        // ArrayList：数组结构，遍历更快
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        System.out.println(list1);

        // LinkedList：双向链表，插入和删除更快
        List<String> list2 = new LinkedList<>();
        list2.add("1");
        list2.add("2");
        System.out.println(list2);

        // 键-值存储：键不能重复，值可以重复
        Map<String, String> map = new HashMap<>(16);
        map.put("key1", "val1");
        map.put("key2", "val2");
        System.out.println(map);

        // HashSet：无序不重复
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("1");
        hashSet.add("2");
        System.out.println(hashSet);
    }

}
