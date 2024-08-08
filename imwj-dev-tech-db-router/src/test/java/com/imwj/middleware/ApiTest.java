package com.imwj.middleware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-08-07 17:23
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IUserDao userDao;

    @Test
    public void test(){
        userDao.insertUser("10001");
    }

}
