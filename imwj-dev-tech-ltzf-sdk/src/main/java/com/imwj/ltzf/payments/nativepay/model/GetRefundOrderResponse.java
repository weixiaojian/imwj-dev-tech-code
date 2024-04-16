package com.imwj.ltzf.payments.nativepay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRefundOrderResponse {

    private Long code;
    private Data data;
    private String msg;
    @JsonProperty("request_id")
    private String requestId;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("refund_status")
        private Long refundStatus;
        @JsonProperty("mch_id")
        private String mchId;
        @JsonProperty("out_trade_no")
        private String outTradeNo;
        @JsonProperty("pay_no")
        private String payNo;
        @JsonProperty("order_no")
        private String orderNo;
        @JsonProperty("out_refund_no")
        private String outRefundNo;
        @JsonProperty("pay_refund_no")
        private String payRefundNo;
        @JsonProperty("refund_fee")
        private String refundFee;
        @JsonProperty("user_received_account")
        private String userReceivedAccount;
        @JsonProperty("success_time")
        private String successTime;
    }

}


