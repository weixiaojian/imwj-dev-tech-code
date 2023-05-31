package com.imwj.design;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * MQ消息体适配类
 *
 * @author wj
 * @create 2023-05-31 14:51
 */
public class MQAdapter {

    public static RebateInfo filter(String strJson, Map<String, String> link) throws Exception {
        return filter(JSON.parseObject(strJson, Map.class), link);
    }

    /**
     * 消息过滤转换
     *
     * @param obj  数据实体
     * @param link key对应关系(key:rebateInfo字段名  value:原先字段名)
     * @return
     */
    public static RebateInfo filter(Map obj, Map<String, String> link) throws Exception {
        RebateInfo rebateInfo = new RebateInfo();
        for (String key : link.keySet()) {
            Object val = obj.get(link.get(key));
            RebateInfo.class.getMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1),
                    String.class).invoke(rebateInfo, val);
        }
        return rebateInfo;
    }

}
