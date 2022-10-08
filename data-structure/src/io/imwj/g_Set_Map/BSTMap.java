package io.imwj.g_Set_Map;

import io.imwj.f_Binary_Tree.BST;
import io.imwj.util.FileOperation;

import java.util.ArrayList;

/**
 * 基于二分搜索树实现的map
 *
 * @author langao_q
 * @since 2021-03-17 17:38
 */
public class BSTMap<K extends Comparable, V> implements Map<K, V> {

    private class Node {
        public K key;
        public V value;
        public Node left;
        public Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;


    @Override
    public void add(K key, V value) {
        root = addByRecursive(root, key, value);
    }

    private Node addByRecursive(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo((K) node.key) < 0) {
            node.left = addByRecursive(node.left, key, value);
        } else if (key.compareTo((K) node.key) > 0) {
            node.right = addByRecursive(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = removeByRecursive(root, key);
            return node.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node != null ? node.value : null;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + "doesn't exist!");
        } else {
            node.value = value;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 根据key获取指定Node元素
     *
     * @param node
     * @param key
     * @return
     */
    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo((K) node.key) < 0) {
            return getNode(node.left, key);
        } else if (key.compareTo((K) node.key) > 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }

    /**
     * 递归删除最小元素  返回删除元素后的根
     *
     * @param node
     */
    private Node removeMinByRecursive(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMinByRecursive(node.left);
        return node;
    }

    /**
     * 递归寻找元素中的最小值
     *
     * @return
     */
    private Node minnumByRecursive(Node node) {
        if (node.left == null) {
            return node;
        }
        return minnumByRecursive(node.left);
    }

    /**
     * 递归删除任意元素
     * 返回删除元素的根
     *
     * @param node
     * @param key
     * @return
     */
    private Node removeByRecursive(Node node, K key) {
        if (node == null) {
            return null;
        }
        //删除左子树
        if (key.compareTo(node.key) < 0) {
            node.left = removeByRecursive(node.left, key);
            return node;
        }
        //删除右子树
        if (key.compareTo(node.key) > 0) {
            node.right = removeByRecursive(node.right, key);
            return node;
        }
        //删除当前元素
        else {
            //待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            //待删除节点右子树为空的情况
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            //待删除节点左/右子树都不为空
            //找到后继值节点[即待删除节点的右子树中的最小节点] 替换掉要删除的元素
            Node successor = minnumByRecursive(node.right);
            successor.right = removeMinByRecursive(node.right);
            successor.left = node.left;

            node.left = node.right = null;
            return successor;
        }
    }


    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            BSTMap<String, Integer> map = new BSTMap<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else {
                    map.add(word, 1);
                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
