package com.imwj.ltzf.payments.nativepay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepayResponse {

    private Long code;
    private Data data;
    private String msg;
    @JsonProperty("request_id")
    private String requestId;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("code_url")
        private String codeUrl;
        @JsonProperty("QRcode_url")
        private String qrcodeUrl;
    }

}
