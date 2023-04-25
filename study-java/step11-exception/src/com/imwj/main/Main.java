package com.imwj.main;

import com.imwj.exception.MyException;

/**
 * @author wj
 * @create 2023-04-25 11:18
 */
public class Main {

    public static void main(String[] args) throws MyException {
        // 空指针异常
        Integer i =null;
        try {
            System.out.println(i.equals(""));
        }catch (NullPointerException e){
            System.out.printf("空指针异常：%s", e);
            // 异常再抛出(自定义异常)
            throw new MyException(e.getMessage());
        }finally {
            System.out.println("无论异常都会执行！");
        }
    }

}
