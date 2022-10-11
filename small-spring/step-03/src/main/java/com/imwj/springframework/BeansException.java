package com.imwj.springframework;

/**
 * bean异常处理类
 * @author wj
 * @create 2022-10-09 17:42
 */
public class BeansException extends Throwable {
    public BeansException(String msg, Exception e) {
        System.out.println(msg + "，e:" + e);
    }

    public BeansException(String msg) {
        System.out.println(msg);
    }
}
