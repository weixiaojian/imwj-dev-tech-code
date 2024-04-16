package com.imwj.ltzf.payments.nativepay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imwj.ltzf.utils.SignUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PrepayRequest {

    @JsonProperty("mch_id")
    private String mchid;
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    @JsonProperty("total_fee")
    private String totalFee;
    @JsonProperty("body")
    private String body;
    @JsonProperty("notify_url")
    private String notifyUrl;
    @JsonProperty("time_expire")
    private String timeExpire;

    private String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

    public String getTimestamp() {
        return timestamp;
    }

    public String createSign(String partnerKey) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("mch_id", getMchid());
        dataMap.put("out_trade_no", getOutTradeNo());
        dataMap.put("total_fee", getTotalFee());
        dataMap.put("body", getBody());
        dataMap.put("timestamp", getTimestamp());
        dataMap.put("notify_url", getNotifyUrl());
        return SignUtils.createSign(dataMap, partnerKey);
    }

}
