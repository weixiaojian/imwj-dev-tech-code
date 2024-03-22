package com.imwj.domain.model.entity;

import com.imwj.domain.model.vo.LogicCheckTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wj
 * @create 2024-03-22 16:33
 * @description 规则动作实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleActionEntity<T> {

    private LogicCheckTypeVO type;
    private T data;

}
