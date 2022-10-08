package io.imwj.g_Set_Map;

/**
 * 集合
 *
 * @author langao_q
 * @since 2021-03-09 17:40
 */
public interface Set<E> {

    void add(E e);

    void remove(E e);

    boolean contains(E e);

    int getSize();

    boolean isEmpty();
}
