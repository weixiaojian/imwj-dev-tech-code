package com.imwj.design.test;

import com.alibaba.fastjson.JSON;
import com.imwj.design.model.aggregates.TreeRich;
import com.imwj.design.model.vo.EngineResult;
import com.imwj.design.model.vo.TreeNode;
import com.imwj.design.model.vo.TreeNodeLink;
import com.imwj.design.model.vo.TreeRoot;
import com.imwj.design.service.engine.IEngine;
import com.imwj.design.service.engine.impl.TreeEngineHandle;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @create 2023-06-01 15:11
 */
public class ApiTest {


    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private TreeRich treeRich;

    @Before
    public void init(){
        // 节点：1
        TreeNode treeNode_01 = new TreeNode();
        treeNode_01.setTreeId(10001L);
        treeNode_01.setTreeNodeId(1L);
        treeNode_01.setNodeType(1);
        treeNode_01.setNodeValue(null);
        treeNode_01.setRuleKey("userGender");
        treeNode_01.setRuleDesc("用户性别[男/女]");
        // 链接：1->11
        TreeNodeLink treeNodeLink_11 = new TreeNodeLink();
        treeNodeLink_11.setNodeIdFrom(1L);
        treeNodeLink_11.setNodeIdTo(11L);
        treeNodeLink_11.setRuleLimitType(1);
        treeNodeLink_11.setRuleLimitValue("man");
        // 链接：1->12
        TreeNodeLink treeNodeLink_12 = new TreeNodeLink();
        treeNodeLink_12.setNodeIdTo(1L);
        treeNodeLink_12.setNodeIdTo(12L);
        treeNodeLink_12.setRuleLimitType(1);
        treeNodeLink_12.setRuleLimitValue("woman");
        List<TreeNodeLink> treeNodeLinkList_1 = new ArrayList<>();
        treeNodeLinkList_1.add(treeNodeLink_11);
        treeNodeLinkList_1.add(treeNodeLink_12);
        treeNode_01.setTreeNodeLinkList(treeNodeLinkList_1);
        // 节点：11
        TreeNode treeNode_11 = new TreeNode();
        treeNode_11.setTreeId(10001L);
        treeNode_11.setTreeNodeId(11L);
        treeNode_11.setNodeType(1);
        treeNode_11.setNodeValue(null);
        treeNode_11.setRuleKey("userAge");
        treeNode_11.setRuleDesc("用户年龄");
        // 链接：11->111
        TreeNodeLink treeNodeLink_111 = new TreeNodeLink();
        treeNodeLink_111.setNodeIdFrom(11L);
        treeNodeLink_111.setNodeIdTo(111L);
        treeNodeLink_111.setRuleLimitType(3);
        treeNodeLink_111.setRuleLimitValue("25");
        // 链接：11->112
        TreeNodeLink treeNodeLink_112 = new TreeNodeLink();
        treeNodeLink_112.setNodeIdFrom(11L);
        treeNodeLink_112.setNodeIdTo(112L);
        treeNodeLink_112.setRuleLimitType(5);
        treeNodeLink_112.setRuleLimitValue("25");
        List<TreeNodeLink> treeNodeLinkList_11 = new ArrayList<>();
        treeNodeLinkList_11.add(treeNodeLink_111);
        treeNodeLinkList_11.add(treeNodeLink_112);
        treeNode_11.setTreeNodeLinkList(treeNodeLinkList_11);
        // 节点：12
        TreeNode treeNode_12 = new TreeNode();
        treeNode_12.setTreeId(10001L);
        treeNode_12.setTreeNodeId(12L);
        treeNode_12.setNodeType(1);
        treeNode_12.setNodeValue(null);
        treeNode_12.setRuleKey("userAge");
        treeNode_12.setRuleDesc("用户年龄");
        // 链接：12->121
        TreeNodeLink treeNodeLink_121 = new TreeNodeLink();
        treeNodeLink_121.setNodeIdFrom(12L);
        treeNodeLink_121.setNodeIdTo(121L);
        treeNodeLink_121.setRuleLimitType(3);
        treeNodeLink_121.setRuleLimitValue("25");
        // 链接：12->122
        TreeNodeLink treeNodeLink_122 = new TreeNodeLink();
        treeNodeLink_122.setNodeIdFrom(12L);
        treeNodeLink_122.setNodeIdTo(122L);
        treeNodeLink_122.setRuleLimitType(5);
        treeNodeLink_122.setRuleLimitValue("25");
        List<TreeNodeLink> treeNodeLinkList_12 = new ArrayList<>();
        treeNodeLinkList_12.add(treeNodeLink_121);
        treeNodeLinkList_12.add(treeNodeLink_122);
        treeNode_12.setTreeNodeLinkList(treeNodeLinkList_12);
        // 节点：111
        TreeNode treeNode_111 = new TreeNode();
        treeNode_111.setTreeId(10001L);
        treeNode_111.setTreeNodeId(111L);
        treeNode_111.setNodeType(2);
        treeNode_111.setNodeValue("果实A");
        // 节点：112
        TreeNode treeNode_112 = new TreeNode();
        treeNode_112.setTreeId(10001L);
        treeNode_112.setTreeNodeId(112L);
        treeNode_112.setNodeType(1);
        treeNode_112.setNodeValue(null);
        treeNode_112.setRuleKey("userPosition");
        treeNode_112.setRuleDesc("用户职级[领导/员工]");

        // 链接：112->1121
        TreeNodeLink treeNodeLink_1121 = new TreeNodeLink();
        treeNodeLink_1121.setNodeIdFrom(112L);
        treeNodeLink_1121.setNodeIdTo(1121L);
        treeNodeLink_1121.setRuleLimitType(1);
        treeNodeLink_1121.setRuleLimitValue("leader");
        // 链接：112->1122
        TreeNodeLink treeNodeLink_1122 = new TreeNodeLink();
        treeNodeLink_1122.setNodeIdTo(112L);
        treeNodeLink_1122.setNodeIdTo(1122L);
        treeNodeLink_1122.setRuleLimitType(1);
        treeNodeLink_1122.setRuleLimitValue("emp");
        List<TreeNodeLink> treeNodeLinkList_112 = new ArrayList<>();
        treeNodeLinkList_112.add(treeNodeLink_1121);
        treeNodeLinkList_112.add(treeNodeLink_1122);
        treeNode_112.setTreeNodeLinkList(treeNodeLinkList_112);
        // 节点：1121
        TreeNode treeNode_1121 = new TreeNode();
        treeNode_1121.setTreeId(10001L);
        treeNode_1121.setTreeNodeId(112L);
        treeNode_1121.setNodeType(2);
        treeNode_1121.setNodeValue("果实B1");
        // 节点：1122
        TreeNode treeNode_1122 = new TreeNode();
        treeNode_1122.setTreeId(10001L);
        treeNode_1122.setTreeNodeId(112L);
        treeNode_1122.setNodeType(2);
        treeNode_1122.setNodeValue("果实B2");

        // 节点：121
        TreeNode treeNode_121 = new TreeNode();
        treeNode_121.setTreeId(10001L);
        treeNode_121.setTreeNodeId(121L);
        treeNode_121.setNodeType(2);
        treeNode_121.setNodeValue("果实C");
        // 节点：122
        TreeNode treeNode_122 = new TreeNode();
        treeNode_122.setTreeId(10001L);
        treeNode_122.setTreeNodeId(122L);
        treeNode_122.setNodeType(2);
        treeNode_122.setNodeValue("果实D");
        // 树根
        TreeRoot treeRoot = new TreeRoot();
        treeRoot.setTreeId(10001L);
        treeRoot.setTreeRootNodeId(1L);
        treeRoot.setTreeName("规则决策树");
        Map<Long, TreeNode> treeNodeMap = new HashMap<>();
        treeNodeMap.put(1L, treeNode_01);
        treeNodeMap.put(11L, treeNode_11);
        treeNodeMap.put(12L, treeNode_12);
        treeNodeMap.put(111L, treeNode_111);
        treeNodeMap.put(112L, treeNode_112);
        treeNodeMap.put(121L, treeNode_121);
        treeNodeMap.put(122L, treeNode_122);
        treeNodeMap.put(1121L, treeNode_1121);
        treeNodeMap.put(1122L, treeNode_1122);
        treeRich = new TreeRich(treeRoot, treeNodeMap);
    }

    @Test
    public void test_tree() {
        logger.info("决策树组合结构信息：\r\n" + JSON.toJSON(treeRich));

        IEngine treeEngineHandle = new TreeEngineHandle();

        /**
         * 测试数据
         * 果实A：gender=man、age=22
         * 果实B：gender=man、age=29
         * 果实B1：gender=man、age=29、position=leader
         * 果实B2：gender=man、age=29、position=emp
         * 果实C：gender=woman、age=22
         * 果实D：gender=woman、age=29
         */
        Map<String, String> decisionMatter = new HashMap<>();
        decisionMatter.put("gender", "man");
        decisionMatter.put("age", "29");
        decisionMatter.put("position", "leader");

        EngineResult result = treeEngineHandle.process(10001L, "Oli09pLkdjh", treeRich, decisionMatter);
        logger.info("测试结果：{}", JSON.toJSONString(result));

    }

}

