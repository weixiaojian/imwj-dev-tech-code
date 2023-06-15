package com.imwj.design;

import java.math.BigDecimal;

/**
 * @author wj
 * @create 2023-06-15 17:49
 */
public class Context<T>{

    private ICouponDiscount<T> couponDiscount;

    public Context(ICouponDiscount<T> couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice) {
        return couponDiscount.discountAmount(couponInfo, skuPrice);
    }

}
