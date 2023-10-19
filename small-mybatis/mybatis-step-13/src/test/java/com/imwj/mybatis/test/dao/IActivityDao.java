package com.imwj.mybatis.test.dao;

import com.imwj.mybatis.test.entity.Activity;

/**
 * @author wj
 * @create 2023-10-19 11:40
 * @description
 */
public interface IActivityDao {

    Activity queryActivityById(Long activityId);

}
