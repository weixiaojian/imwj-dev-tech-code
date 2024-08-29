package com.imwj.big.market.domain.activity.model.aggregate;

import com.imwj.big.market.domain.activity.model.entity.ActivityAccountEntity;
import com.imwj.big.market.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-08-28 15:02
 * @description 下单聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 增加；总次数
     */
    private Long totalCount;

    /**
     * 增加；日次数
     */
    private Long dayCount;

    /**
     * 增加；月次数
     */
    private Long monthCount;

    /**
     * 活动订单实体
     */
    private ActivityOrderEntity activityOrderEntity;


}
