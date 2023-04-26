package com.imwj.main;

import com.imwj.util.MyArrayList;

import java.util.ArrayList;

/**
 * @author wj
 * @create 2023-04-26 11:42
 */
public class Main {

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add(i);
        }
        System.out.println(list);

        //? extends通配符：能取出(取出类型为父类)
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("111");
        ArrayList<? extends Object> heroList = objectList;
        System.out.println(heroList.get(0));


        //? super通配符：能放入，只能取出父类
        ArrayList<? super Object> objectList1 = new ArrayList<>();
        objectList1.add(new String("111"));
        objectList1.add(new Integer(222));
        System.out.println(objectList1);

        Object o = objectList1.get(0);
        System.out.println(o);
    }

}
