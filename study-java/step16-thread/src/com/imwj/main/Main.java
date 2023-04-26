package com.imwj.main;

/**
 * @author wj
 * @create 2023-04-26 15:48
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 主线程：此处主线程会正常向下执行，而子线程会和主线程并行执行
        System.out.println("111");
        // 子线程
        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i < 100; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(i);
                }
            }
        }.start();
        // 主线程
        System.out.println("222");
    }

}
