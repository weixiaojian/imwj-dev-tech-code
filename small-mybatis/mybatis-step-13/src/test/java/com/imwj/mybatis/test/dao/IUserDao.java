package com.imwj.mybatis.test.dao;

import com.imwj.mybatis.annotations.Insert;
import com.imwj.mybatis.annotations.Select;
import com.imwj.mybatis.annotations.Update;
import com.imwj.mybatis.test.entity.User;

import java.util.List;

/**
 * @author wj
 * @create 2023-07-20 10:25
 * @description
 */
public interface IUserDao {

    List<User> queryUserInfoList();

}
