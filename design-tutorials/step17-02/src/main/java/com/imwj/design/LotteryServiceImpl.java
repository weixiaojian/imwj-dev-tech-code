package com.imwj.design;

import java.util.Date;

/**
 * @author wj
 * @create 2023-06-13 14:59
 */
public class LotteryServiceImpl extends LotteryService{
    @Override
    protected LotteryResult doDraw(String uId) {
        // 摇号
        // String lottery = minibusTargetService.lottery(uId);
        String lottery = "测试摇号!";
        // 结果
        return new LotteryResult(uId, lottery, new Date());
    }
}
