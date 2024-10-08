package com.imwj.big.market.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-08-28 15:03
 * @description 活动次数实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCountEntity {

    /**
     * 活动次数编号
     */
    private Long activityCountId;

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

}

