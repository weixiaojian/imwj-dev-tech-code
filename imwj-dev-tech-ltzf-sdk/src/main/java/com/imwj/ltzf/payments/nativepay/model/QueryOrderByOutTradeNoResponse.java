package com.imwj.ltzf.payments.nativepay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryOrderByOutTradeNoResponse {

    /** 返回状态，枚举值：0：成功 1：失败 */
    private Long code;
    /** 返回数据 */
    private Data data;
    /** 消息 */
    private String msg;
    /** 唯一请求ID，每次请求都会返回，定位问题时需要提供该次请求的request_id。 */
    @JsonProperty("request_id")
    private String requestId;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        /** 下单时间 */
        @JsonProperty("add_time")
        private String addTime;
        /** 商户号 */
        @JsonProperty("mch_id")
        private String mchId;
        /** 系统订单号 */
        @JsonProperty("order_no")
        private String orderNo;
        /** 商户订单号 */
        @JsonProperty("out_trade_no")
        private String outTradeNo;
        /** 微信支付订单号，当支付状态为已支付时返回此参数。 */
        @JsonProperty("pay_no")
        private String payNo;
        /** 商品描述 */
        private String body;
        /** 支付金额 */
        @JsonProperty("total_fee")
        private String totalFee;
        /** 支付类型，枚举值：
         NATIVE：扫码支付
         H5：H5支付
         APP：APP支付
         JSAPI：公众号支付
         MINIPROGRAM：小程序支付
         示例值：NATIVE */
        @JsonProperty("trade_type")
        private String tradeType;
        /** 支付完成时间，当支付状态为已支付时返回此参数。 */
        @JsonProperty("success_time")
        private String successTime;
        /** 附加数据，在支付接口中填写的数据，可作为自定义参数使用。 */
        private String attach;
        /** 支付者信息，当支付状态为已支付时返回此参数。 */
        @JsonProperty("openid")
        private String openid;
        /** 支付状态，枚举值：0：未支付 1：已支付 */
        @JsonProperty("pay_status")
        private Integer payStatus;

    }

}

