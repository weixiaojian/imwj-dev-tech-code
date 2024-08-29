package com.imwj.big.market.domain.activity.model.entity;

import com.imwj.big.market.domain.activity.model.valobj.OrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wj
 * @create 2024-08-28 15:03
 * @description 活动参与实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityOrderEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * sku
     */
    private Long sku;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 总次数
     */
    private Long totalCount;

    /**
     * 日次数
     */
    private Long dayCount;

    /**
     * 月次数
     */
    private Long monthCount;

    /**
     * 订单状态
     */
    private OrderStateVO state;
    /**
     * 业务仿重ID - 外部透传的，确保幂等
     */
    private String outBusinessNo;

}
