package com.imwj.design.test;


import com.imwj.design.Builder;
import com.imwj.design.IMenu;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author wj
 * @create 2023-05-29 17:33
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private Builder builder = new Builder();

    @Test
    public void test_Builder(){
        IMenu one = builder.levelOne(100D);
        System.out.println(one.getDetail());

        IMenu two = builder.levelTwo(100D);
        System.out.println(two.getDetail());

        IMenu three = builder.levelThree(100D);
        System.out.println(three.getDetail());

    }


}
