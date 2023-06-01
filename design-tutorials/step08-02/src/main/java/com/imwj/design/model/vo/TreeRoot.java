package com.imwj.design.model.vo;

import lombok.Data;

/**
 * @author wj
 * @create 2023-06-01 13:47
 */
@Data
public class TreeRoot {

    private Long treeId;         //规则树ID
    private Long treeRootNodeId; //规则树根ID
    private String treeName;     //规则树名称

}
