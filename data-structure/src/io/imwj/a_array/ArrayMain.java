package io.imwj.a_array;

/**
 * 数组操作类
 *
 * @author LANGAO
 * @create 2020-05-19 15:23
 */
public class ArrayMain {

    public static void main(String[] args) {
        Array<Integer> array = new Array<>();
        for (int i = 0; i < 7; i++) {
            array.addLast(i);
        }
        System.out.println("初始化" + array);

        array.add(1, 10);
        System.out.println("add后" + array);

        array.addFirst(-10);
        System.out.println("addFirst后" + array);

        array.set(0, -1);
        System.out.println("set后" + array);

        array.remove(0);
        System.out.println("remove后" + array);

        array.removeFirst();
        System.out.println("removeFirst后" + array);

        array.removeLast();
        System.out.println("removeLast后" + array);

        array.removeElement(10);
        System.out.println("removeElement后" + array);

        for (int i = 0; i < 20; i++) {
            array.addLast(i);
            System.out.println(array);
        }

        for (int i = 0; i < 20; i++) {
            array.removeLast();
            System.out.println(array);
        }
    }

    /**
     * 数组初始化
     */
    public void init() {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
