package com.imwj.design.good;

import com.alibaba.fastjson.JSON;

/**
 * 实物商品服务
 * @author wj
 * @create 2023-05-24 17:27
 */
public class GoodsService {

    public Boolean deliverGoods(DeliverReq req) {
        System.out.println("模拟发货实物商品一个：" + JSON.toJSONString(req));
        return true;
    }

}
