package io.imwj.d_LinkedList;

/**
 * @author langao_q
 * @since 2021-02-22 15:19
 */
public class LinkedList<E> {

    /**
     * 使用虚拟头节点
     */
    private Node dummyHead;
    private int size;

    /**
     * 构造方法
     */
    public LinkedList() {
        this.dummyHead = new Node(null, null);
        this.size = 0;
    }

    /**
     * 返回链表长度
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 判断链表是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在链表头添加元素e
     * 1.把元素e转换成newNode节点
     * 2.将newNode的next指向原始的head
     * 3.原始的head指向newNode
     *
     * @param e
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在指定位置插入元素
     * 1.把元素e转换成newNode节点
     * 2.找到指定位置的前一个节点数据prev
     * 3.将newNode的next指向prev的next，prev的next指向newNode
     *
     * @param index
     * @param e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }
        //遍历元素：初始化时 prev=head
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        /*Node newNode = new Node(e);
        newNode.next = prev.next;
        prev.next = newNode;*/
        prev.next = new Node(e, prev.next);
        size++;
    }

    /**
     * 在链表末尾添加元素
     *
     * @param e
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 获取指定位置的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }
        //遍历元素：此处和add时不同  add是要获取指定位置的前一个元素，get是要获取指定位置元素
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return (E) cur.e;
    }

    /**
     * 获取头节点的元素
     *
     * @return
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获取末尾节点的元素
     *
     * @return
     */
    public E getLast() {
        return get(size);
    }

    /**
     * 修改指定位置节点的元素值
     * 遍历元素
     *
     * @param index
     * @param e
     */
    public void set(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }
        //遍历元素：此处和add时不同  add是要获取指定位置的前一个元素，set是要获取指定位置元素
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    /**
     * 查看链表中是否有指定元素
     *
     * @param e
     * @return
     */
    public boolean contains(E e) {
        Node cur = dummyHead.next;
        while (cur != null) {
            if (cur.e.equals(e)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * 删除指定位置的节点
     * 1.获取删除节点的前一个节点prevNode
     * 2.prevNode.next指向delNode的next
     * 3.delNode指向null
     *
     * @param index
     */
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }
        //遍历获取待删除元素的前一个节点
        Node prevNode = dummyHead;
        for (int i = 0; i < index; i++) {
            prevNode = prevNode.next;
        }
        //获取待删除元素
        Node delNode = prevNode.next;
        //赋值
        prevNode.next = delNode.next;
        delNode.next = null;
        size--;
        return (E) delNode.e;
    }

    /**
     * 删除头节点
     *
     * @return
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 删除末尾节点
     *
     * @return
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除指定元素 然后返回链表集合
     *
     * @param e
     * @return
     */
    public void removeElement(E e) {
        Node prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.e.equals(e)) {
                break;
            }
            prev = prev.next;
        }

        if (prev.next != null) {
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        Node cur = dummyHead.next;
        while (cur != null) {
            str.append(cur + "->");
            cur = cur.next;
        }
        return str.toString();
    }
}
