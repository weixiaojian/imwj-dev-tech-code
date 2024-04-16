package com.imwj.ltzf.payments.nativepay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imwj.ltzf.utils.SignUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GetRefundOrderRequest {

    @JsonProperty("mch_id")
    private String mchid;
    @JsonProperty("out_refund_no")
    private String outRefundNo;

    private String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

    /** 创建签名 */
    public String createSign(String partnerKey) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("mch_id", getMchid());
        dataMap.put("out_refund_no", getOutRefundNo());
        dataMap.put("timestamp", getTimestamp());
        return SignUtils.createSign(dataMap, partnerKey);
    }

}
