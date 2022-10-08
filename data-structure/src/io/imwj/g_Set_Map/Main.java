package io.imwj.g_Set_Map;

import io.imwj.d_LinkedList.LinkedList;
import io.imwj.util.FileOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author langao_q
 * @since 2021-03-09 17:47
 */
public class Main {


    public static void main(String[] args) {
        String fileName = "pride-and-prejudice.txt";
        Set<String> one = new BSTSet<>();
        System.out.println(testSet(one, fileName));

        Set<String> two = new LinkedListSet<>();
        System.out.println(testSet(two, fileName));
    }

    /**
     * 测试集合
     *
     * @param set
     * @param filename
     * @return
     */
    private static double testSet(Set<String> set, String filename) {

        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            for (String word : words) {
                set.add(word);
            }
            System.out.println("Total different words: " + set.getSize());
        }
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    private static void read() {
        //1.使用list
        ArrayList<String> list = new ArrayList<>();
        FileOperation.readFile("pride-and-prejudice.txt", list);
        System.out.println("Total size：" + list.size());

        //2.使用Set
        LinkedListSet<String> set = new LinkedListSet<>();
        for (String str : list) {
            set.add(str);
        }
        System.out.println("Total size：" + set.getSize());
    }

}
