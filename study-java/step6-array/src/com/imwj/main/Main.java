package com.imwj.main;

/**
 * 数组
 * @author wj
 * @create 2023-04-23 16:46
 */
public class Main {

    public static void main(String[] args) {
        // 创建数组
        int[] arr = {1,2,3};
        System.out.println(arr.length);

        // 赋值
        arr[2] = 5;
        System.out.println(arr[2]);

        // 遍历
        for(int a : arr){
            System.out.println(a + "-");
        }

        // 二维数组
        int[][] arrTo = {{1,2},{3,5}};
        System.out.println(arrTo[0][1]);
        for(int[] at : arrTo){
            for(int a : at){
                System.out.println(a + "--");
            }
        }
    }

}
