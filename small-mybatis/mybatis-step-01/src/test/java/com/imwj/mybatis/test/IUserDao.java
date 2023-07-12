package com.imwj.mybatis.test;

/**
 * @author wj
 * @create 2023-07-11 17:45
 * @description
 */
public interface IUserDao {

    String queryUserName(String uId);

    Integer queryUserAge(String uId);

}
