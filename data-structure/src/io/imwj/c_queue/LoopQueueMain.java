package io.imwj.c_queue;

import java.util.Random;

/**
 * 循环队列测试
 * @author LANGAO
 * @create 2020-05-20 16:28
 */
public class LoopQueueMain {

    public static void main(String[] args) throws Exception {

    }

    /**
     * 测试使用q运行opCount个enqueueu和dequeue操作所需要的时间，单位：秒
     */
    public static double testQueue(Queue<Integer> q, int opCount) throws Exception {

        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0 ; i < opCount ; i ++){
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0 ; i < opCount ; i ++){
            q.dequeue();
        }
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    /**
     * 测试入队和出队
     */
    public static void fun1(){
        LoopQueue<Object> queue = new LoopQueue(10);
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            System.out.println(queue);
            if(i % 2 == 0){
                queue.dequeue();
            }
        }
    }
}
