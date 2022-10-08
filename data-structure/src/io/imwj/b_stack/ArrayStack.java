package io.imwj.b_stack;

import io.imwj.a_array.Array;
import io.imwj.base.Stack;

/**
 * 自定义栈的实现
 * 基于动态数组实现
 *
 * @author LANGAO
 * @create 2020-05-20 11:26
 */
public class ArrayStack<E> implements Stack<E> {

    private Array<E> array;

    /**
     * 自定义长度的
     *
     * @param capacity
     */
    public ArrayStack(int capacity) {
        array = new Array<>(capacity);
    }

    /**
     * 使用默认长度的
     */
    public ArrayStack() {
        array = new Array<>();
    }

    @Override
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 获取栈的容量
     *
     * @return
     */
    public int getCapacity() {
        return array.getCapacity();
    }

    @Override
    public String toString() {
        return array + " >>> top ";
    }
}
