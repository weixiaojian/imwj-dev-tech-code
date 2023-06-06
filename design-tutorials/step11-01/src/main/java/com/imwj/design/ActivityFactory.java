package com.imwj.design;

import com.imwj.design.util.RedisUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 *
 * @author wj
 * @create 2023-06-06 11:24
 */
public class ActivityFactory {

    /**
     * 商品缓存map：key(商品id)，val(商品信息)
     */
    static Map<Long, Activity> activityMap = new HashMap<Long, Activity>();

    public static Activity queryInfo(Long activityId) {
        Activity activity = activityMap.get(activityId);
        // 初始化基础信息
        if (activity == null) {
            activity = Activity.builder()
                    .id(activityId)
                    .name("图书嗨乐")
                    .desc("图书优惠券分享激励分享活动第二期")
                    .startTime(new Date())
                    .startTime(new Date())
                    .build();
            activityMap.put(activityId, activity);
        }
        return activity;
    }
}
