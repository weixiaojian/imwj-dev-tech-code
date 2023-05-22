package com.imwj.design.service.impl;

/**
 * 扩展继承，实现自己的需求
 * @author wj
 * @create 2023-05-22 11:17
 */
public class CalculationAreaExt extends CalculationArea{


    private final static double π = 3.141592653D;

    @Override
    public double circular(double r) {
        return π * r * r;
    }
}
