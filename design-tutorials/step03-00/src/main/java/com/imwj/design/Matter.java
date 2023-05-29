package com.imwj.design;

import java.math.BigDecimal;

/**
 * 装修物料
 * @author wj
 * @create 2023-05-29 17:16
 */
public interface Matter {

    /**
     * 场景；地板、地砖、涂料、吊顶
     */
    String scene();

    /**
     * 品牌
     */
    String brand();

    /**
     * 型号
     */
    String model();

    /**
     * 平米报价
     */
    BigDecimal price();

    /**
     * 描述
     */
    String desc();

}
