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


    @Select("SELECT id, userId, userName, userHead " +
            "FROM user " +
            "where id = #{id}")
    User queryUserInfoById(Long id);

    @Select("SELECT id, userId, userName, userHead " +
            "        FROM user " +
            "        where id = #{id}")
    User queryUserInfo(User req);

    @Select("SELECT id, userId, userName, userHead FROM user")
    List<User> queryUserInfoList();

    @Update("UPDATE user " +
            "SET userName = #{userName} " +
            "WHERE id = #{id}")
    int updateUserInfo(User req);

    @Insert("INSERT INTO user " +
            "(userId, userName, userHead, createTime, updateTime) " +
            "VALUES (#{userId}, #{userName}, #{userHead}, now(), now())")
    void insertUserInfo(User req);

    @Insert("DELETE FROM user WHERE userId = #{userId}")
    int deleteUserInfoByUserId(String userId);

}
