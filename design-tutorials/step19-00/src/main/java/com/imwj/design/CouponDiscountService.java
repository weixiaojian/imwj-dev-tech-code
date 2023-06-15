package com.imwj.design;

/**
 * @author wj
 * @create 2023-06-15 17:39
 */
public class CouponDiscountService {

    /**
     * 最终金额计算
     * @param type 类型
     * @param typeContent 优惠金额
     * @param skuPrice 原价格
     * @param typeExt 满减（需满金额）
     * @return
     */
    public double discountAmount(int type, double typeContent, double skuPrice, double typeExt) {
        // 1. 直减券
        if (1 == type) {
            return skuPrice - typeContent;
        }
        // 2. 满减券
        if (2 == type) {
            if (skuPrice < typeExt) return skuPrice;
            return skuPrice - typeContent;
        }
        // 3. 折扣券
        if (3 == type) {
            return skuPrice * typeContent;
        }
        // 4. n元购
        if (4 == type) {
            return typeContent;
        }
        return 0D;
    }

}
