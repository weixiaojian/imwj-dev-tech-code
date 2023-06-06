package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 库存实体
 * @author wj
 * @create 2023-06-06 11:16
 */
@Data
@AllArgsConstructor
public class Stock {


    /** 库存总量 */
    private int total;
    /** 库存已用 */
    private int used;
}
