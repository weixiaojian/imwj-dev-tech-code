package com.imwj.mybatis.scripting.xmltags;

/**
 * @author wj
 * @create 2023-08-18 16:50
 * @description 静态文本SQL节点
 */
public class StaticTextSqlNode implements SqlNode{

    private String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public boolean apply(DynamicContext context) {
        //将文本加入context
        context.appendSql(text);
        return true;
    }

}