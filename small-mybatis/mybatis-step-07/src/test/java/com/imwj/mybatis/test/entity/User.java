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
}
