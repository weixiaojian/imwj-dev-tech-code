package io.imwj.b_stack;

import java.util.Stack;

/**
 * leetcode20号题：有效的括号
 *
 * @author LANGAO
 * @create 2020-05-20 11:46
 */
public class leetcode20 {
    Integer d;

    public static void main(String[] args) {
        leetcode20 l = new leetcode20();
        boolean valid = l.isValid("([)]");
        System.out.println(valid);
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char topChar = stack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
