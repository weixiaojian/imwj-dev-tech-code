package com.imwj.ltzf.payments.nativepay.model;

import com.imwj.ltzf.utils.SignUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class QueryOrderByOutTradeNoRequest {

    /** 商户号 */
    @Setter
    private String mchid;
    /** 商户订单号 */
    @Setter
    private String outTradeNo;
    /** 当前时间戳 */
    private final String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

    /** 创建签名 */
    public String createSign(String partnerKey) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("mch_id", getMchid());
        dataMap.put("out_trade_no", getOutTradeNo());
        dataMap.put("timestamp", getTimestamp());
        return SignUtils.createSign(dataMap, partnerKey);
    }

}
