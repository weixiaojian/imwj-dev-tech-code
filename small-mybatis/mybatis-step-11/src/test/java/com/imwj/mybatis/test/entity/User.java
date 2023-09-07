package com.imwj.mybatis.test.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wj
 * @create 2023-07-27 10:59
 * @description
 */
@Data
public class User {

    private Long id;
    // 用户ID
    private String userId;
    // 用户名称
    private String userName;
    // 头像
    private String userHead;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }
    public User(long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public User(long id, String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }
}
