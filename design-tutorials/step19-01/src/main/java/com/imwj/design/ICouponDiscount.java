package com.imwj.design;

import java.math.BigDecimal;

/**
 * 优惠券接口
 * @author wj
 * @create 2023-06-15 17:44
 */
public interface ICouponDiscount<T> {

    /**
     * 优惠券金额计算
     * @param couponInfo 券折扣信息；直减、满减、折扣、N元购
     * @param skuPrice   sku金额
     * @return           优惠后金额
     */
    BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice);
}
