package com.imwj.exception;

/**
 * @author wj
 * @create 2023-04-25 11:22
 */
public class MyException extends Exception{

    public MyException(){

    }
    public MyException(String msg){
        super(msg);
    }
}
