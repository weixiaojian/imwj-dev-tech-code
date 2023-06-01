package com.imwj.design.service.engine;

import com.imwj.design.service.logic.LogicFilter;
import com.imwj.design.service.logic.impl.UserAgeFilter;
import com.imwj.design.service.logic.impl.UserGenderFilter;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wj
 * @create 2023-06-01 14:06
 */
public class EngineConfig {

    static Map<String, LogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    static {
        logicFilterMap.put("userAge", new UserAgeFilter());
        logicFilterMap.put("userGender", new UserGenderFilter());

    }

    public Map<String, LogicFilter> getLogicFilterMap() {
        return logicFilterMap;
    }

    public void setLogicFilterMap(Map<String, LogicFilter> logicFilterMap) {
        EngineConfig.logicFilterMap = logicFilterMap;
    }
}
