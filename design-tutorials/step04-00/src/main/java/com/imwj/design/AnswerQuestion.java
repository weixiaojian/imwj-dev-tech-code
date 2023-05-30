package com.imwj.design;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 问答题
 * @author wj
 * @create 2023-05-30 10:43
 */
@Data
@AllArgsConstructor
public class AnswerQuestion {


    private String name;  // 问题
    private String key;   // 答案
}
