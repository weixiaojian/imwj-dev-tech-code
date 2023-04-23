package com.imwj.main;

import java.util.Scanner;

/**
 * 操作符：
 * @author wj
 * @create 2023-04-23 15:03
 */
public class Main {

    public static void main(String[] args) {
        // 操作符-算术：+、-、*、/、%
        System.out.println(1 + 1);
        System.out.println(2 - 1);
        System.out.println(2 * 2);
        System.out.println(6 / 3);
        System.out.println(7 % 3);

        System.out.println("-------");

        // 自增、自减：++、--
        int i = 1;
        System.out.println(i++);
        System.out.println(i--);

        System.out.println("-------");

        // 关系操作符：>、>=、<、<=、==、!=
        System.out.println(2 > 1);
        System.out.println(2 >= 1);
        System.out.println(2 < 1);
        System.out.println(2 <= 1);
        System.out.println(2 == 1);
        System.out.println(2 != 1);

        System.out.println("-------");

        // 逻辑操作符：&、&&、|、||、！、^
        System.out.println(1 == 1 & 2 == 2);
        System.out.println(1 == 1 && 2 == 2);
        System.out.println(1 == 1 | 2 == 2);
        System.out.println(1 == 1 || 2 == 2);
        System.out.println(1 != 1);
        System.out.println(true ^ true);

        System.out.println("-------");

        // 赋值操作符：=、+=、-=、*=、/=、%=、|=、^=、<<=、>>=、>>>=
        i = 0;
        System.out.println(i = 5);
        System.out.println(i += 5);
        System.out.println(i -= 5);
        System.out.println(i *= 5);
        System.out.println(i /= 5);
        System.out.println(i %= 5);
        System.out.println(i |= 5);
        System.out.println(i <<= 5);
        System.out.println(i >>= 5);
        System.out.println(i >>>= 5);

        System.out.println("-------");

        // 三元操作符：?:;
        System.out.println(1==1?'1':'0');

        System.out.println("-------");

        // 输入操作符：Scanner
        Scanner scanner = new Scanner(System.in);
        System.out.println("第一个输入值："+scanner.next());
    }

}
