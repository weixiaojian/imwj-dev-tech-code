package com.imwj.mybatis.builder;

import com.imwj.mybatis.mapping.ResultMap;
import com.imwj.mybatis.mapping.ResultMapping;

import java.util.List;

/**
 * @author wj
 * @create 2023-10-18 17:59
 * @description 结果映射解析器
 */
public class ResultMapResolver {

    private final MapperBuilderAssistant assistant;
    private String id;
    private Class<?> type;
    private List<ResultMapping> resultMappings;

    public ResultMapResolver(MapperBuilderAssistant assistant, String id, Class<?> type, List<ResultMapping> resultMappings) {
        this.assistant = assistant;
        this.id = id;
        this.type = type;
        this.resultMappings = resultMappings;
    }

    public ResultMap resolve() {
        return assistant.addResultMap(this.id, this.type, this.resultMappings);
    }

}