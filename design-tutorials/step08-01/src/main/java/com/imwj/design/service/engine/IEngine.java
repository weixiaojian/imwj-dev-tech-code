package com.imwj.design.service.engine;

import com.imwj.design.model.aggregates.TreeRich;
import com.imwj.design.model.vo.EngineResult;

import java.util.Map;

/**
 * 决策引擎接口定义
 * @author wj
 * @create 2023-06-01 13:49
 */
public interface IEngine {

    /**
     * 决策引擎处理
     * @param treeId 树id
     * @param userId 用户id
     * @param treeRich 聚合对象
     * @param decisionMatter
     * @return
     */
    EngineResult process(final Long treeId, final String userId, TreeRich treeRich, final Map<String, String> decisionMatter);

}
