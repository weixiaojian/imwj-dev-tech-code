package com.imwj.big.market.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author wj
 * @create 2024-05-23 17:06
 * @description 抽奖活动订单
 */
@Data
public class RaffleActivityOrder {

    /** 自增ID */
    private Long id;
    /** 用户ID */
    private String userId;
    /** sku */
    private Long sku;
    /** 活动ID */
    private Long activityId;
    /** 活动名称 */
    private String activityName;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 订单ID */
    private String orderId;
    /** 下单时间 */
    private Date orderTime;
    private Long totalCount;
    private Long dayCount;
    private Long monthCount;
    /** 订单状态（not_used、used、expire） */
    private String state;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
    /**
     * 业务仿重ID - 外部透传的，确保幂等
     */
    private String outBusinessNo;

}