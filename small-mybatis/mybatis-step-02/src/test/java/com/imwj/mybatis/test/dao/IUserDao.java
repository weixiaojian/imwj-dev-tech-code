package com.imwj.mybatis.test.dao;

/**
 * @author wj
 * @create 2023-07-20 10:25
 * @description
 */
public interface IUserDao {

    String queryUserName(String uId);

    Integer queryUserAge(String uId);
}
