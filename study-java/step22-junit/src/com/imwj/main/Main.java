package com.imwj.main;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-04-27 16:04
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("main方法测试！");
    }

    @Test
    public void testSum1() {
        int result = 1 + 1;
        // 实际值、期望值，如果两者不一致则无法通过测试
        Assert.assertEquals(result, 2);
        System.out.println("junit方法测试！");
    }

    @Test
    public void testSum2() {
        int result = 1 + 2;
        // 实际值、期望值，如果两者不一致则无法通过测试
        Assert.assertEquals(result, 2);
        System.out.println("junit方法测试！");
    }

    @Before
    public void before() {
        System.out.println("测试前的准备工作，比如链接数据库等等");
    }
    @After
    public void after() {
        System.out.println("测试结束后的工作，比如关闭链接等等");
    }

}
