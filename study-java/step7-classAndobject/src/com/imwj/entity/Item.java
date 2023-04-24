package com.imwj.entity;

/**
 * @author wj
 * @create 2023-04-24 10:22
 */
public class Item extends Hero{

    public Item(){

    }

    public Item(int price, int damage, String name){
        this.price = price;
        this.damage = damage;
        super.name = name;
    }

    public int price;
    public int damage;

    public static int age;

    public String toString(String test) {
        return test;
    }

    @Override
    public String toString() {
        return "Item{" +
                "price=" + price +
                ", damage=" + damage +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", armor=" + armor +
                ", moveSpeed=" + moveSpeed +
                '}';
    }
}
