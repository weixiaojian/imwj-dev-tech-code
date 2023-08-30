package com.imwj.mybatis.test.dao;

import com.imwj.mybatis.test.entity.User;

/**
 * @author wj
 * @create 2023-07-20 10:25
 * @description
 */
public interface IUserDao {

    User queryUserInfoById(User user);
}
