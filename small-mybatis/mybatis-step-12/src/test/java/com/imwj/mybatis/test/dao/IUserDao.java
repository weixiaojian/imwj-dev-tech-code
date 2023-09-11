package com.imwj.mybatis.test.dao;

import com.imwj.mybatis.test.entity.User;

import java.util.List;

/**
 * @author wj
 * @create 2023-07-20 10:25
 * @description
 */
public interface IUserDao {

    User queryUserInfoById(Long id);

    User queryUserInfo(User req);

    List<User> queryUserInfoList();

    int updateUserInfo(User req);

    void insertUserInfo(User req);

    int deleteUserInfoByUserId(String userId);
}
