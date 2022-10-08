package io.imwj.b_stack;

/**
 * ArrayStack测试
 *
 * @author LANGAO
 * @create 2020-05-20 11:35
 */
public class ArrayStackMain {

    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack();
        for (int i = 0; i < 5; i++) {
            stack.push(i);
            System.out.println(stack);
        }

        for (int i = 0; i < 5; i++) {
            Integer pop = stack.pop();
            System.out.println(stack);
        }
    }
}
