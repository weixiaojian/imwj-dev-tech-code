package com.imwj.design.model.aggregates;

import com.imwj.design.model.vo.TreeNode;
import com.imwj.design.model.vo.TreeRoot;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * 聚合对象，包含组织树信息
 * @author wj
 * @create 2023-06-01 13:44
 */
@Data
@AllArgsConstructor
public class TreeRich {

    private TreeRoot treeRoot;                          //树根信息
    private Map<Long, TreeNode> treeNodeMap;        //树节点ID -> 子节点
}
