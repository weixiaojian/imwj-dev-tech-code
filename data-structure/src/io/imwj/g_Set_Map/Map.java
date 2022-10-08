package io.imwj.g_Set_Map;

/**
 * map映射接口
 * @author langao_q
 * @since 2021-03-12 16:26
 */
public interface Map<K, V> {

    void add(K key, V value);

    V remove(K key);

    boolean contains(K key);

    V get(K key);

    void set(K key, V value);

    int getSize();

    boolean isEmpty();


}
