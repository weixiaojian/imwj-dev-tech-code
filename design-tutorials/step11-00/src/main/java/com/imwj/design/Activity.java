package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品实体
 * @author wj
 * @create 2023-06-06 11:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    /** 活动ID */
    private Long id;
    /** 活动名称 */
    private String name;
    /** 活动描述 */
    private String desc;
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date stopTime;
    /** 活动库存 */
    private Stock stock;
}
