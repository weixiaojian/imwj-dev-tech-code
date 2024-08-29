package com.imwj.big.market.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author wj
 * @create 2024-05-23 17:04
 * @description 抽奖活动账户表
 */
@Data
public class RaffleActivityAccount {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;
    /** 总次数 */
    private Long totalCount;
    /** 总次数-剩余 */
    private Long totalCountSurplus;
    /** 日次数 */
    private Long dayCount;
    /** 日次数-剩余 */
    private Long dayCountSurplus;
    /** 月次数 */
    private Long monthCount;
    /** 月次数-剩余 */
    private Long monthCountSurplus;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
