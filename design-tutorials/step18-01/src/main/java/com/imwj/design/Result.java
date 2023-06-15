package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wj
 * @create 2023-06-15 16:27
 */
@Data
@AllArgsConstructor
public class Result {
    private String code; // 编码
    private String info; // 描述
}
