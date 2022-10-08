package io.imwj.d_LinkedList;

/**
 * @author langao_q
 * @since 2021-02-22 15:31
 */
public class Node<E> {
    E e;
    Node next;

    /**
     * 构造函数-toString方法
     *
     * @param e
     * @param next
     */
    public Node(E e, Node next) {
        this.e = e;
        this.next = next;
    }

    public Node(E e) {
        this(e, null);
    }

    public Node() {
        this(null, null);
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
