package com.imwj.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-03-22 16:33
 * @description 规则内容实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatterEntity {

    private String content;

}
