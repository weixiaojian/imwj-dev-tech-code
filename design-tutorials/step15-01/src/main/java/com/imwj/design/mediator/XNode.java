package com.imwj.design.mediator;

import lombok.Data;

import java.util.Map;

/**
 * @author wj
 * @create 2023-06-12 16:35
 */
@Data
public class XNode {

    private String namespace;
    private String id;
    private String parameterType;
    private String resultType;
    private String sql;
    private Map<Integer, String> parameter;

}
