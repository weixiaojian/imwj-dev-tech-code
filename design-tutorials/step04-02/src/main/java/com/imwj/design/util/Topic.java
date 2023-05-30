package com.imwj.design.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * 题目选项乱序操作工具包
 * @author wj
 * @create 2023-05-30 11:15
 */
@Data
@AllArgsConstructor
public class Topic {

    private Map<String, String> option;  // 选项；A、B、C、D
    private String key;           // 答案；B

}
