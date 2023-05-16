package com.imwj.entity;

/**
 * @author wj
 * @create 2023-04-23 10:45
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
