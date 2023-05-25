package com.imwj.design;

/**
 * 发奖结果反馈对象
 * @author wj
 * @create 2023-05-24 17:44
 */
public class AwardRes {


    private String code; // 编码
    private String info; // 描述

    public AwardRes(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
