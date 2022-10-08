package io.imwj.base;

/**
 * 实现自定义栈的接口
 *
 * @author LANGAO
 * @create 2020-05-20 11:19
 */
public interface Stack<E> {

    /**
     * 向栈中添加一个元素
     *
     * @param e
     */
    void push(E e);

    /**
     * 出栈
     *
     * @return
     */
    E pop();

    /**
     * 查看栈顶元素
     *
     * @return
     */
    E peek();

    /**
     * 获取栈的大小
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
