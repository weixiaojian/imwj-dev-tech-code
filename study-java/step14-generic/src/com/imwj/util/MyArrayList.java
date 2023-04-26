package com.imwj.util;

import java.util.Arrays;

/**
 * @author wj
 * @create 2023-04-26 11:33
 */
public class MyArrayList<T> {

    private T[] data;
    private int size;

    /**
     * 默认数组长度是10
     */
    public MyArrayList(){
        this(10);
    }

    /**
     * 构造函数，传入的是数组的容量capacity构造Array
     * 此处数组类型不能指定为E泛型，泛型是从JDK1.5后才开始支持
     * @param capacity
     */
    public MyArrayList(int capacity){
        data = (T[]) new Object[capacity];
        size = 0;
    }

    /**
     * 添加元素
     * @param t
     */
    public void add(T t){
        // 数组长度超出 需要扩容
        if(size == data.length){
            T[] newData = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
        data[size] = t;
        size ++;
    }


    @Override
    public String toString() {
        return "MyArrayList{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }
}
