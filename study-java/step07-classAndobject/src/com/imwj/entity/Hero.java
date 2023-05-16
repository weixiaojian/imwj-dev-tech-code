package com.imwj.entity;

/**
 * 英雄类
 * @author wj
 * @create 2023-04-24 10:15
 */
public class Hero {

    /**
     * 姓名
     */
    public String name;
    /**
     * 血量
     */
    public Float hp;
    /**
     * 护甲
     */
    public Float armor;
    /**
     * 移动速度
     */
    public Integer moveSpeed;


    /**
     * 方法重载
     * @return
     */
    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", armor=" + armor +
                ", moveSpeed=" + moveSpeed +
                '}';
    }
}
