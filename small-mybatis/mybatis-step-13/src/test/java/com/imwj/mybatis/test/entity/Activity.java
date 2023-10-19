package com.imwj.mybatis.test.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wj
 * @create 2023-10-19 11:39
 * @description
 */
@Data
public class Activity {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动描述
     */
    private String activityDesc;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}