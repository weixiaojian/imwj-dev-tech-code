package com.imwj.mybatis.builder;

import com.imwj.mybatis.mapping.MappedStatement;
import com.imwj.mybatis.mapping.ResultMap;
import com.imwj.mybatis.mapping.SqlCommandType;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.scripting.LanguageDriver;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.SqlSession;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2023-08-31 11:28
 * @description 映射构建器助手，建造者
 */
@Data
public class MapperBuilderAssistant extends BaseBuilder{

    private String currentNamespace;
    private String resource;

    public MapperBuilderAssistant(Configuration configuration, String resource) {
        super(configuration);
        this.resource = resource;
    }

    /**
     * 增加指定前缀
     */
    public String applyCurrentNamespace(String base, boolean isReference){
        if(base == null){
            return null;
        }
        if(isReference){
            if (base.contains(".")) return base;
        }
        return currentNamespace + "." + base;
    }

    /**
     * 添加映射器语句
     */
    public MappedStatement addMappedStatement(
        String id,
        SqlSource sqlSource,
        SqlCommandType sqlCommandType,
        Class<?> parameterType,
        String resultMap,
        Class<?> resultType,
        LanguageDriver lang
    ){
        // 给id加上namespace前缀
        id = applyCurrentNamespace(id, false);
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlCommandType, sqlSource, resultType);

        // 结果映射，给MappedStatement
        setStatmentResultMap(resultMap, resultType, statementBuilder);

        MappedStatement statement = statementBuilder.build();
        // 映射语句信息，建造完成存入配置项
        configuration.addMappedStatement(statement);
        return statement;
    }

    /**
     * 结果映射
     */
    private void setStatmentResultMap(String resultMap, Class<?> resultType, MappedStatement.Builder statementBuilder) {
        // 因为暂时没有再XML中配置map返回结果 此处返回null
        applyCurrentNamespace(resultMap, true);
        List<ResultMap> resultMaps = new ArrayList<>();
        if(resultMap != null){
            // TODO：暂无Map结果映射配置，本章节不添加此逻辑
        }
        /**
         * 通常使用resultType即可满足大部分场景
         * <select id="queryUserById" resultType="com.imwj.mybatis">
         * 使用resultType的情况下，Mtbatis会自动创建一个ResultMap 基于属性名映射列到JavaBean属性上
         */
        else if(resultType != null){
            ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
                    configuration,
                    statementBuilder.id() + "-Inline",
                    resultType,
                    new ArrayList<>());
            resultMaps.add(inlineResultMapBuilder.build());
        }
        statementBuilder.resultMaps(resultMaps);
    }

}
