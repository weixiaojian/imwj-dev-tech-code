package io.imwj.f_Binary_Tree;

/**
 * @author langao_q
 * @since 2021-03-05 16:49
 */
public class Main {

    public static void main(String[] args) {
        BST<Integer> b = new BST<>();
        int[] nums = {5,3,6,8,4,2};
        for(Integer num : nums){
           b.add(num);
        }

        //              5
        //      3               6
        //  2       4               8

        //5 3 2 6 4 8
        //b.preOrder();

        //2 3 4 5 6 8
        //b.inOrder();

        //2 4 3 8 6 5
        //b.psotOrder();

        //5 3 6 2 4 8
        //b.levelOrder();

        //2---8
        //Integer minnum = b.minnum();
        //Integer maxxum = b.maxxum();
        //System.out.println(minnum + "---" + maxxum);

        //Integer removeMin = b.removeMin();
        //System.out.println(removeMin);

        //Integer removeMax = b.removeMax();
        //System.out.println(removeMax);

        b.removeTo(8);
        b.levelOrder();

    }

}
