package com.imwj.mybatis.builder.annotation;

import com.imwj.mybatis.annotations.Delete;
import com.imwj.mybatis.annotations.Insert;
import com.imwj.mybatis.annotations.Select;
import com.imwj.mybatis.annotations.Update;
import com.imwj.mybatis.binding.MapperMethod;
import com.imwj.mybatis.builder.MapperBuilderAssistant;
import com.imwj.mybatis.mapping.SqlCommandType;
import com.imwj.mybatis.mapping.SqlSource;
import com.imwj.mybatis.scripting.LanguageDriver;
import com.imwj.mybatis.session.Configuration;
import com.imwj.mybatis.session.ResultHandler;
import com.imwj.mybatis.session.RowBounds;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author wj
 * @create 2023-09-12 11:37
 * @description 注解配置构建器
 */
public class MapperAnnotationBuilder {

    private final Set<Class<? extends Annotation>> sqlAnnotationTypes = new HashSet<>();

    private Configuration configuration;
    private MapperBuilderAssistant assistant;
    private Class<?> type;


    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        String resource = type.getName().replace(".", "/") + ".java (best guess)";
        this.assistant = new MapperBuilderAssistant(configuration, resource);
        this.configuration = configuration;
        this.type = type;

        sqlAnnotationTypes.add(Select.class);
        sqlAnnotationTypes.add(Insert.class);
        sqlAnnotationTypes.add(Update.class);
        sqlAnnotationTypes.add(Delete.class);
    }

    public void parse(){
        String resource = type.toString();
        if(!configuration.isResourceLoaded(resource)){
            assistant.setCurrentNamespace(type.getName());
            Method[] methods = type.getMethods();
            for(Method method : methods){
                if(!method.isBridge()){
                    // 解析语句
                    parseStatement(method);
                }
            }
        }
    }

    /**
     * 解析语句
     * @param method
     */
    private void parseStatement(Method method) {
        Class<?> parameterTypeClass = getParameterType(method);
        LanguageDriver languageDriver = getLanguageDriver(method);
        SqlSource sqlSource = getSqlSourceFromAnnotations(method, parameterTypeClass, languageDriver);
        if(sqlSource != null){
            final String mappedStatementId = method.getName();
            SqlCommandType sqlCommandType = getSqlCommandType(method);
            boolean isSelect = sqlCommandType == SqlCommandType.SELECT;

            String resultMapId = null;
            if(isSelect){
                resultMapId = parseResultMap(method);
            }
            // 调用助手类
            assistant.addMappedStatement(
                    mappedStatementId,
                    sqlSource,
                    sqlCommandType,
                    parameterTypeClass,
                    resultMapId,
                    getReturnType(method),
                    languageDriver
            );
        }
    }

    /**
     * 获取请求参数类型
     * @param method
     * @return
     */
    private Class<?> getParameterType(Method method) {
        Class<?> parameterType = null;
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> clazz : parameterTypes) {
            if (!RowBounds.class.isAssignableFrom(clazz) && !ResultHandler.class.isAssignableFrom(clazz)) {
                if (parameterType == null) {
                    parameterType = clazz;
                } else {
                    parameterType = MapperMethod.ParamMap.class;
                }
            }
        }
        return parameterType;
    }

    /**
     * 获取脚本语言处理器
     * @param method
     * @return
     */
    private LanguageDriver getLanguageDriver(Method method) {
        Class<?> langClass = configuration.getLanguageRegistry().getDefaultDriverClass();
        return configuration.getLanguageRegistry().getDriver(langClass);
    }

    /**
     * 从注解中获取SqlSource
     * @param method
     * @param parameterType
     * @param languageDriver
     * @return
     */
    private SqlSource getSqlSourceFromAnnotations(Method method, Class<?> parameterType, LanguageDriver languageDriver) {
        try {
            Class<? extends Annotation> sqlAnnotationType = getSqlAnnotationType(method);
            if (sqlAnnotationType != null) {
                Annotation sqlAnnotation = method.getAnnotation(sqlAnnotationType);
                final String[] strings = (String[]) sqlAnnotation.getClass().getMethod("value").invoke(sqlAnnotation);
                return buildSqlSourceFromStrings(strings, parameterType, languageDriver);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Could not find value method on SQL annotation.  Cause: " + e);
        }
    }

    /**
     * 获取sql注解类型
     * @param method
     * @return
     */
    private Class<? extends Annotation> getSqlAnnotationType(Method method) {
        for (Class<? extends Annotation> type : sqlAnnotationTypes) {
            Annotation annotation = method.getAnnotation(type);
            if (annotation != null) return type;
        }
        return null;
    }

    /**
     * 生成sqlSource
     * @param strings
     * @param parameterTypeClass
     * @param languageDriver
     * @return
     */
    private SqlSource buildSqlSourceFromStrings(String[] strings, Class<?> parameterTypeClass, LanguageDriver languageDriver) {
        final StringBuilder sql = new StringBuilder();
        for (String fragment : strings) {
            sql.append(fragment);
            sql.append(" ");
        }
        return languageDriver.createSqlSource(configuration, sql.toString(), parameterTypeClass);
    }

    /**
     * 获取sql操作类型
     * @param method
     * @return
     */
    private SqlCommandType getSqlCommandType(Method method) {
        Class<? extends Annotation> type = getSqlAnnotationType(method);
        if (type == null) {
            return SqlCommandType.UNKNOWN;
        }
        return SqlCommandType.valueOf(type.getSimpleName().toUpperCase(Locale.ENGLISH));
    }

    private String parseResultMap(Method method) {
        // generateResultMapName
        StringBuilder suffix = new StringBuilder();
        for (Class<?> c : method.getParameterTypes()) {
            suffix.append("-");
            suffix.append(c.getSimpleName());
        }
        if (suffix.length() < 1) {
            suffix.append("-void");
        }
        String resultMapId = type.getName() + "." + method.getName() + suffix;

        // 添加 ResultMap
        Class<?> returnType = getReturnType(method);
        assistant.addResultMap(resultMapId, returnType, new ArrayList<>());
        return resultMapId;
    }

    /**
     * 重点：DAO 方法的返回类型，如果为 List 则需要获取集合中的对象类型
     */
    private Class<?> getReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        if (Collection.class.isAssignableFrom(returnType)) {
            Type returnTypeParameter = method.getGenericReturnType();
            if (returnTypeParameter instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnTypeParameter).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (issue #443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if (returnTypeParameter instanceof GenericArrayType) {
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (issue #525) support List<byte[]>
                        returnType = Array.newInstance(componentType, 0).getClass();
                    }
                }
            }
        }
        return returnType;
    }

}
