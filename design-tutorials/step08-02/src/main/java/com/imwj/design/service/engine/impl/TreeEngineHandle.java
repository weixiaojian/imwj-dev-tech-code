package com.imwj.design.service.engine.impl;

import com.imwj.design.model.aggregates.TreeRich;
import com.imwj.design.model.vo.EngineResult;
import com.imwj.design.model.vo.TreeNode;
import com.imwj.design.service.engine.EngineBase;

import java.util.Map;

/**
 * 决策引擎的实现
 * @author wj
 * @create 2023-06-01 14:22
 */
public class TreeEngineHandle extends EngineBase {

    @Override
    public EngineResult process(Long treeId, String userId, TreeRich treeRich, Map<String, String> decisionMatter) {
        // 决策流程
        TreeNode treeNode = engineDecisionMaker(treeRich, treeId, userId, decisionMatter);
        // 决策结果
        return new EngineResult(userId, treeId, treeNode.getTreeNodeId(), treeNode.getNodeValue());
    }
}
