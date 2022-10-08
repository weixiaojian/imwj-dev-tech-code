package io.imwj.e_LinkedList_Recursive;

/**
 * @author langao_q
 * @since 2021-02-24 10:49
 */
public class Sum {


    /**
     * 递归方式数组求和
     * @param arr
     * @return
     */
    public static int sum(int[] arr){
        return recursive(arr, 0);
    }

    /**
     * 计算从index到最后一位的和
     * @param arr
     * @param index
     * @return
     */
    public static int recursive(int[] arr, int index){
        if(index == arr.length){
            return 0;
        }
        return arr[index] + recursive(arr, index + 1);
    }

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};
        int sum = sum(arr);
        System.out.println(sum);
    }
}
