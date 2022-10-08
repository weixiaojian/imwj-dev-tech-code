package io.imwj.c_queue;

/**
 * 实现自定义队列的接口
 *
 * @author LANGAO
 * @create 2020-05-20 15:32
 */
public interface Queue<E> {
    /**
     * 向队列中添加一个元素
     *
     * @param e
     */
    void enqueue(E e);

    /**
     * 出队
     *
     * @return
     */
    E dequeue() throws Exception;

    /**
     * 查看队首元素
     *
     * @return
     */
    E getFront();

    /**
     * 获取队列的大小
     *
     * @return
     */
    int getSize();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();
}
