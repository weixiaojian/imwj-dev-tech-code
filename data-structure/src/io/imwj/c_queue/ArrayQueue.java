package io.imwj.c_queue;

import io.imwj.a_array.Array;

/**
 * 自定义数组队列的实现
 * 基于动态数组实现
 *
 * @author LANGAO
 * @create 2020-05-20 15:35
 */
public class ArrayQueue<E> implements Queue<E> {

    private Array<E> array;

    /**
     * 自定义长度的
     *
     * @param capacity
     */
    public ArrayQueue(int capacity) {
        array = new Array<>(capacity);
    }

    /**
     * 使用默认长度的
     */
    public ArrayQueue() {
        array = new Array<>();
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        return "top<<< " + array + " <<<tail";
    }
}
