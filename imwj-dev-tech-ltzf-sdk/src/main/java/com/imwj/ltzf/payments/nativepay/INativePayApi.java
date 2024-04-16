package com.imwj.ltzf.payments.nativepay;

import com.imwj.ltzf.payments.nativepay.model.*;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author wj
 * @create 2024-04-16 16:44
 * @description 扫码支付接口调用(Retrofit2),文档地址https://www.ltzf.cn/doc
 */
public interface INativePayApi {


    /**
     * 扫码支付
     * @param mchId 商户id
     * @param outTradeNo 订单好
     * @param totalFee 总金额
     * @param body 内容
     * @param timestamp 时间戳
     * @param notifyUrl 回调地址
     * @param sign 签名
     * @return
     */
    @POST("api/wxpay/native")
    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded")
    Call<PrepayResponse> prepay(@Field("mch_id") String mchId,
                                @Field("out_trade_no") String outTradeNo,
                                @Field("total_fee") String totalFee,
                                @Field("body") String body,
                                @Field("timestamp") String timestamp,
                                @Field("notify_url") String notifyUrl,
                                @Field("sign") String sign);

    /**
     * 查询订单
     * @param mchId
     * @param outTradeNo
     * @param timestamp
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("api/wxpay/get_pay_order")
    @Headers("content-type: application/x-www-form-urlencoded")
    Call<QueryOrderByOutTradeNoResponse> getPayOrder(
            @Field("mch_id") String mchId,
            @Field("out_trade_no") String outTradeNo,
            @Field("timestamp") String timestamp,
            @Field("sign") String sign
    );

    /**
     * 退回订单
     * @param mchId
     * @param outTradeNo
     * @param outRefundNo
     * @param timestamp
     * @param refundFee
     * @param refundDesc
     * @param notifyUrl
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("api/wxpay/refund_order")
    Call<RefundOrderResponse> refundOrder(
            @Field("mch_id") String mchId,
            @Field("out_trade_no") String outTradeNo,
            @Field("out_refund_no") String outRefundNo,
            @Field("timestamp") String timestamp,
            @Field("refund_fee") String refundFee,
            @Field("refund_desc") String refundDesc,
            @Field("notify_url") String notifyUrl,
            @Field("sign") String sign
    );

    /**
     * 查询退回订单
     * @param mchId
     * @param outRefundNo
     * @param timestamp
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("api/wxpay/get_refund_order")
    @Headers("content-type: application/x-www-form-urlencoded")
    Call<GetRefundOrderResponse> getRefundOrder(
            @Field("mch_id") String mchId,
            @Field("out_refund_no") String outRefundNo,
            @Field("timestamp") String timestamp,
            @Field("sign") String sign
    );
}

