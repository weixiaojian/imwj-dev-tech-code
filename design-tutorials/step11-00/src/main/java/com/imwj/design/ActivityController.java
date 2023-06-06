package com.imwj.design;

import java.util.Date;

/**
 * @author wj
 * @create 2023-06-06 11:14
 */
public class ActivityController {

    public Activity queryActivityInfo(Long id) {
        // 模拟从实际业务应用从接口中获取活动信息
        Activity activity = new Activity();
        activity.setId(10001L);
        activity.setName("图书嗨乐");
        activity.setDesc("图书优惠券分享激励分享活动第二期");
        activity.setStartTime(new Date());
        activity.setStopTime(new Date());
        // TODO 每次都要新建库存对象在高qps时内存消耗大
        activity.setStock(new Stock(1000,1));
        return activity;
    }
}
