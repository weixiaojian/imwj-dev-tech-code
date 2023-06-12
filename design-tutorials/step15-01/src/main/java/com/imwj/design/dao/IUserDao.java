package com.imwj.design.dao;

import com.imwj.design.entity.User;

/**
 * @author wj
 * @create 2023-06-12 11:40
 */
public interface IUserDao {

    User queryUserInfoById(String id);
}
