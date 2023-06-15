package com.imwj.design;

import lombok.Data;

import java.util.Date;

/**
 * 流程信息
 * @author wj
 * @create 2023-06-15 16:23
 */
@Data
public class ActivityInfo {

    private String activityId;    // 活动ID
    private String activityName;  // 活动名称
    private Enum<Status> status;  // 活动状态
    private Date beginTime;       // 开始时间
    private Date endTime;         // 结束时间
}
