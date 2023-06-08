package com.imwj.design.cuisine.impl;

import com.imwj.design.cook.ICook;
import com.imwj.design.cuisine.ICuisine;

/**
 * @author wj
 * @create 2023-06-08 17:30
 */
public class GuangDoneCuisine implements ICuisine {

    private ICook cook;

    private GuangDoneCuisine() {
    }

    public GuangDoneCuisine(ICook cook) {
        this.cook = cook;
    }

    @Override
    public void cook() {
        cook.doCooking();
    }

}