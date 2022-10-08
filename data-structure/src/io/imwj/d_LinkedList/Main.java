package io.imwj.d_LinkedList;

import io.imwj.b_stack.ArrayStack;
import io.imwj.base.Stack;
import io.imwj.c_queue.ArrayQueue;
import io.imwj.c_queue.LoopQueue;
import io.imwj.c_queue.LoopQueueMain;
import io.imwj.c_queue.Queue;

import java.util.Random;

/**
 * @author langao_q
 * @since 2021-02-22 17:44
 */
public class Main {

    public static void main(String[] args) throws Exception {
        testQueuePerformance();
    }

    /**
     * 测试链表
     */
    public static void testLinkedList(){
        LinkedList<Integer> list = new LinkedList();

        //赋值
        for(int i=0; i<5; i++){
            list.addFirst(i);
            System.out.println(list);
        }
        //向头部添加
        list.addLast(-1);
        System.out.println(list);

        //向指定位置添加
        list.add(2, 99);
        System.out.println(list);

        list.set(2, 88);
        System.out.println(list);

        //删除头/尾
        list.removeFirst();
        System.out.println(list);
        list.removeLast();
        System.out.println(list);
    }

    /**
     * 测试链表实现的栈
     */
    public static void testLinkedListStack(){
        LinkedListStack<Integer> list = new LinkedListStack();

        //赋值
        for(int i=0; i<5; i++){
            list.push(i);
            System.out.println(list);
        }

        //查看栈顶数据
        Integer peek = list.peek();
        System.out.println(peek);

        //取出数据
        for(int i=0; i<5; i++){
            Integer pop = list.pop();
            System.out.println(pop);
        }
        System.out.println(list);
    }

    /**
     * 性能比对：数组实现的栈/链表实现的栈
     */
    public static void testPerformance(){
        int opCount = 100000;

        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        double time1 = testStack(arrayStack, opCount);
        System.out.println("ArrayStack, time: " + time1 + " s");

        //其实这个时间比较很复杂，因为LinkedListStack中包含更多的new操作
        LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();
        double time2 = testStack(linkedListStack, opCount);
        System.out.println("LinkedListStack, time: " + time2 + " s");
    }
    private static double testStack(Stack<Integer> stack, int opCount){

        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0 ; i < opCount ; i ++) {
            stack.push(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0 ; i < opCount ; i ++) {
            stack.pop();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    /**
     * 测试使用链表实现的队列Queue
     * @throws Exception
     */
    public static void testLinkedListQueue() throws Exception {
        LinkedListQueue<Integer> list = new LinkedListQueue();
        //入队
        for(int i=0; i<5; i++){
            list.enqueue(i);
            System.out.println(list);
        }

        //查看栈顶数据
        Integer peek = list.getFront();
        System.out.println(peek);

        //出队
        for(int i=0; i<5; i++){
            Integer dequeue = list.dequeue();
            System.out.println(list);
        }
        System.out.println(list);
    }

    /**
     * 测试使用q运行opCount个enqueueu和dequeue操作所需要的时间，单位：秒
     * @throws Exception
     */
    public static void testQueuePerformance() throws Exception {
        int opCount = 100000;

        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        double time1 = LoopQueueMain.testQueue(arrayQueue, opCount);
        System.out.println("ArrayQueue, time: " + time1 + " s");

        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        double time2 = LoopQueueMain.testQueue(loopQueue, opCount);
        System.out.println("LoopQueue, time: " + time2 + " s");

        LinkedListQueue<Integer> linkedListQueue = new LinkedListQueue<>();
        double time3 = LoopQueueMain.testQueue(linkedListQueue, opCount);
        System.out.println("LinkedListQueue, time: " + time3 + " s");
    }

}
