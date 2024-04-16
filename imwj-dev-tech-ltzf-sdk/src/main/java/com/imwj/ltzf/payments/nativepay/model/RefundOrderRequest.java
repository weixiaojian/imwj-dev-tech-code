package com.imwj.ltzf.payments.nativepay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imwj.ltzf.utils.SignUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RefundOrderRequest {

    @JsonProperty("mch_id")
    private String mchid;
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    @JsonProperty("out_refund_no")
    private String outRefundNo;
    @JsonProperty("refund_fee")
    private String refundFee;
    @JsonProperty("refund_desc")
    private String refundDesc;
    @JsonProperty("notify_url")
    private String notifyUrl;

    private String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

    /** 创建签名 */
    public String createSign(String partnerKey) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("mch_id", getMchid());
        dataMap.put("out_trade_no", getOutTradeNo());
        dataMap.put("out_refund_no", getOutRefundNo());
        dataMap.put("refund_fee", getRefundFee());
        dataMap.put("timestamp", getTimestamp());
        return SignUtils.createSign(dataMap, partnerKey);
    }

}
