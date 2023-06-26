package com.imwj.design.user.impl;

import com.imwj.design.user.User;
import com.imwj.design.visitor.Visitor;

import java.math.BigDecimal;

/**
 * 老师类
 * @author wj
 * @create 2023-06-26 18:03
 */
public class Teacher extends User {
    public Teacher(String name, String identity, String clazz) {
        super(name, identity, clazz);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * 升本率
     * @return
     */
    public double entranceRatio() {
        return BigDecimal.valueOf(Math.random() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
