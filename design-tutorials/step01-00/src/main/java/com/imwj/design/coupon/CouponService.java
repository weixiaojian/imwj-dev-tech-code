package com.imwj.design.coupon;

/**
 * 优惠券服务
 * @author wj
 * @create 2023-05-24 17:26
 */
public class CouponService {

    public CouponResult sendCoupon(String uId, String couponNumber, String uuid) {
        System.out.println("模拟发放优惠券一张：" + uId + "," + couponNumber + "," + uuid);
        return new CouponResult("0000", "发放成功");
    }


}
