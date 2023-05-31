package com.imwj.design;

import lombok.Data;

import java.util.Date;

/**
 * 统一的MQ消息体
 * @author wj
 * @create 2023-05-31 14:51
 */
@Data
public class RebateInfo {

    private String userId;  // 用户ID
    private String bizId;   // 业务ID
    private Date bizTime;   // 业务时间
    private String desc;    // 业务描述
}
