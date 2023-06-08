package com.imwj.design.cook.impl;

import com.imwj.design.cook.ICook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-06-08 17:28
 */
public class GuangDongCook implements ICook{

    private Logger logger = LoggerFactory.getLogger(ICook.class);

    @Override
    public void doCooking() {
        logger.info("广东厨师，烹饪粤菜，宫廷菜系，以孔府风味为龙头");
    }
}
