package com.imwj.design.service.logic.impl;

import com.imwj.design.service.logic.BaseLogic;

import java.util.Map;

/**
 * 性别过滤器
 * @author wj
 * @create 2023-06-01 14:04
 */
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(Long treeId, String userId, Map<String, String> decisionMatter) {
        return decisionMatter.get("gender");
    }
}
