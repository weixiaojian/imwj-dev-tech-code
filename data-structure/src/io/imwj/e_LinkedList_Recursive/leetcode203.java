package io.imwj.e_LinkedList_Recursive;

import com.sun.xml.internal.ws.addressing.ProblemHeaderQName;
import io.imwj.d_LinkedList.Node;

/**
 * @author langao_q
 * @since 2021-02-23 17:57
 */
public class leetcode203 {

    /**
     * 节点内部类
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        /**
         * 链表节点的构造函数
         * 使用arr为参数，创建一个链表，当前的ListNode为链表头结点
         *
         * @param arr
         */
        public ListNode(int[] arr) {
            if (arr == null || arr.length == 0) {
                throw new IllegalArgumentException("arr can not be empty");
            }
            this.val = arr[0];
            ListNode cur = this;
            for (int i = 1; i < arr.length; i++) {
                cur.next = new ListNode(arr[i]);
                cur = cur.next;
            }
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            ListNode cur = this;
            while (cur != null) {
                str.append(cur.val + "->");
                cur = cur.next;
            }
            return str.toString();
        }
    }

    public static void main(String[] args) {

        int[] nums = {1, 2, 6, 3, 4, 5, 6};
        ListNode head = new ListNode(nums);
        System.out.println(head);

        ListNode res = removeElements5(head, 6);
        System.out.println(res);
    }

    /**
     * 删除指定元素 然后返回链表集合
     * [1,2,6,3,4,5,6]
     * 6
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements(ListNode head, int val) {
        //1.先处理要删除元素在头节点的情况
        while (head != null && head.val == val) {
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
        }

        //2.先处理要删除元素在中间节点的情况
        if (head == null) {
            return null;
        }
        ListNode prevNode = head;
        while (prevNode.next != null) {
            if (prevNode.next.val == val) {
                prevNode.next = prevNode.next.next;
            } else {
                prevNode = prevNode.next;
            }
        }
        return head;
    }

    /**
     * 删除指定元素 然后返回链表集合
     * 使用虚拟头节点的方式实现
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements2(ListNode head, int val) {
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        //1.直接处理要删除元素在中间节点的情况
        ListNode prevNode = dummyHead;
        while (prevNode.next != null) {
            if (prevNode.next.val == val) {
                prevNode.next = prevNode.next.next;
            } else {
                prevNode = prevNode.next;
            }
        }
        return dummyHead.next;
    }

    /**
     * 删除指定元素 然后返回链表集合
     * 使用递归方法实现
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements5(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        head.next = removeElements5(head.next, val);
        return head.val == val ? head.next : head;
    }
}
