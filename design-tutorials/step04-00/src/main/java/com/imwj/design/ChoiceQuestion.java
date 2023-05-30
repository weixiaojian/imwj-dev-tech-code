package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * 选择题类
 * @author wj
 * @create 2023-05-30 10:40
 */
@Data
@AllArgsConstructor
public class ChoiceQuestion {


    private String name;                 // 题目
    private Map<String, String> option;  // 选项；A、B、C、D
    private String key;                  // 答案；B

}
