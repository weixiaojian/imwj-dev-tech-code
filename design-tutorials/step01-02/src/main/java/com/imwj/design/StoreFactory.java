package com.imwj.design;

import com.imwj.design.store.ICommodity;
import com.imwj.design.store.impl.CardCommodityService;
import com.imwj.design.store.impl.CouponCommodityService;
import com.imwj.design.store.impl.GoodsCommodityService;

/**
 * 工厂
 * @author wj
 * @create 2023-05-24 17:52
 */
public class StoreFactory {


    /**
     * 奖品类型方式实例化
     * @param commodityType 奖品类型
     * @return              实例化对象
     */
    public ICommodity getCommodityService(Integer commodityType) {
        if (null == commodityType) return null;
        if (1 == commodityType) return new CouponCommodityService();
        if (2 == commodityType) return new GoodsCommodityService();
        if (3 == commodityType) return new CardCommodityService();
        throw new RuntimeException("不存在的奖品服务类型");
    }

    /**
     * 奖品类信息方式实例化
     * @param clazz 奖品类
     * @return      实例化对象
     */
    public ICommodity getCommodityService(Class<? extends ICommodity> clazz) throws IllegalAccessException, InstantiationException {
        if (null == clazz) return null;
        return clazz.newInstance();
    }

}
