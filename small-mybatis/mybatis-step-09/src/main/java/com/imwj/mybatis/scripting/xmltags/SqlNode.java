package com.imwj.mybatis.scripting.xmltags;

/**
 * @author wj
 * @create 2023-08-18 16:36
 * @description SQL 节点
 */
public interface SqlNode {

    boolean apply(DynamicContext context);


}
