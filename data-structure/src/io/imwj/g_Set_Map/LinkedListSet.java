package io.imwj.g_Set_Map;

import io.imwj.d_LinkedList.LinkedList;

/**
 * 基于链表实现的集合
 *
 * @author langao_q
 * @since 2021-03-09 17:59
 */
public class LinkedListSet<E> implements Set<E> {

    private LinkedList list;

    public LinkedListSet() {
        list = new LinkedList();
    }

    @Override
    public void add(E e) {
        if (!list.contains(e)) {
            list.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
        list.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
