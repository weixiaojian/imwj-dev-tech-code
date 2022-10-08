package io.imwj.c_queue;

/**
 * @author LANGAO
 * @create 2020-05-20 15:41
 */
public class ArrayQueueMain {

    public static void main(String[] args) {
        ArrayQueue<Integer> arrayQueue = new ArrayQueue();
        for (int i = 0; i < 10; i++) {
            arrayQueue.enqueue(i);
            if (i % 2 == 0) {
                arrayQueue.dequeue();
            }
            System.out.println(arrayQueue);
        }

    }
}
