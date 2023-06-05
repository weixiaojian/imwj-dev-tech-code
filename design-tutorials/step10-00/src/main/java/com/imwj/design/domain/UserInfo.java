package com.imwj.design.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wj
 * @create 2023-06-05 16:19
 */
@Data
@AllArgsConstructor
public class UserInfo {

    private String code;
    private String info;

    private String name;
    private Integer age;
    private String address;

    public UserInfo(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public UserInfo(String code, String info) {
        this.code = code;
        this.info = info;
    }
}
