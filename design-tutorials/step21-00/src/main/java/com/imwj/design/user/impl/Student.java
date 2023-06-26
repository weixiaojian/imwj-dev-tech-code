package com.imwj.design.user.impl;

import com.imwj.design.user.User;
import com.imwj.design.visitor.Visitor;

/**
 * @author wj
 * @create 2023-06-26 18:04
 */
public class Student extends User {

    public Student(String name, String identity, String clazz) {
        super(name, identity, clazz);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * 排名
     * @return
     */
    public int ranking() {
        return (int) (Math.random() * 100);
    }
}
