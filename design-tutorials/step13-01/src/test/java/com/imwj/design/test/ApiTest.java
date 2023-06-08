package com.imwj.design.test;

import com.imwj.design.XiaoEr;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-06-08 17:24
 */
public class ApiTest {

    @Test
    public void test(){
        XiaoEr xiaoEr = new XiaoEr();
        xiaoEr.order(1);
        xiaoEr.order(2);
        xiaoEr.order(3);
        xiaoEr.order(4);
        xiaoEr.placeOrder();
    }

}
