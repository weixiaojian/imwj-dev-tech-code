package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 返回实体
 * @author wj
 * @create 2023-06-13 14:27
 */
@Data
@AllArgsConstructor
public class LotteryResult {


    private String uId;    // 用户ID
    private String msg;    // 摇号信息
    private Date dateTime; // 业务时间
}
