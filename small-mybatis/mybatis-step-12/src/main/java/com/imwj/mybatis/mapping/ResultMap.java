package com.imwj.mybatis.mapping;

import com.imwj.mybatis.session.Configuration;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wj
 * @create 2023-08-31 11:22
 * @description 结果映射
 */
@Data
public class ResultMap {

    private String id;
    private Class<?> type;
    private List<ResultMapping> resultMappings;
    private Set<String> mappedColumns;

    public static class Builder {
        private ResultMap resultMap = new ResultMap();

        public Builder(Configuration configuration, String id, Class<?> type, List<ResultMapping> resultMappings) {
            resultMap.id = id;
            resultMap.type = type;
            resultMap.resultMappings = resultMappings;
        }
        public ResultMap build() {
            resultMap.mappedColumns = new HashSet<>();
            return resultMap;
        }

    }

}
