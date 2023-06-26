package com.imwj.design.user;

import com.imwj.design.visitor.Visitor;

/**
 * 用户抽象类
 * @author wj
 * @create 2023-06-26 18:01
 */
public abstract class User {
    public String name;      // 姓名
    public String identity;  // 身份；重点班、普通班 | 特级教师、普通教师、实习教师
    public String clazz;     // 班级

    public User(String name, String identity, String clazz) {
        this.name = name;
        this.identity = identity;
        this.clazz = clazz;
    }

    // 核心访问方法
    public abstract void accept(Visitor visitor);
}
