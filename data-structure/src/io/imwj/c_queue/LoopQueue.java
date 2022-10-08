package io.imwj.c_queue;


import java.util.Arrays;

/**
 * 自定义循环队列的实现
 * 基于动态数组实现
 *
 * @author LANGAO
 * @create 2020-05-20 16:03
 */
public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    /**
     * front队首指针
     * tail队尾指针
     */
    private int front, tail;

    private int size;

    /**
     * 有参数构造：指定长度的队列
     *
     * @param capacity
     */
    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    /**
     * 无参数构造
     */
    public LoopQueue() {
        this(10);
    }


    @Override
    public int getSize() {
        return size;
    }

    /**
     * 获取队列的容量
     * 循环队列会有意的浪费调一个节点 所以要减一
     *
     * @return
     */
    public int getCapacity() {
        return data.length - 1;
    }

    /**
     * 当队首和队尾指针相同时 队列就为空了
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return front == tail;
    }


    @Override
    public void enqueue(E e) {
        //当队尾+1 % 容量 == 队首时说明队列已经满了
        if ((tail + 1) % data.length == front) {
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }


    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("dequeue 出队失败 === 队列为空！");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("dequeue 出队失败 === 队列为空！");
        }
        return data[front];
    }

    /**
     * 扩容方法 队列满了之后才扩容 每次扩容capacity * 2
     * 注意：队列数组中我们有意识的浪费了一个节点
     * 队尾的指针可能指向index为2的位置，所以迁移元素的时候要注意，同时还要考虑数组越界的问题
     * resize以后队首指向index为0的位置，队尾指向size位置
     *
     * @param newCapacity
     */
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public String toString() {
        return "LoopQueue{" +
                "data=" + Arrays.toString(data) +
                ", front=" + front +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }
}
