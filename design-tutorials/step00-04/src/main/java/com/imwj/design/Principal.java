package com.imwj.design;

import java.util.HashMap;
import java.util.Map;

/**
 * 校长
 * @author wj
 * @create 2023-05-22 15:03
 */
public class Principal {
    private Teacher teacher = new Teacher("丽华", "3年1班");

    // 查询班级信息，总分数、学生人数、平均值
    public Map<String, Object> queryClazzInfo(String clazzId) {
        // 获取班级信息；学生总人数、总分、平均分
        int stuCount = teacher.clazzStudentCount();
        double totalScore = teacher.clazzTotalScore();
        double averageScore = teacher.clazzAverageScore();

        // 组装对象，实际业务开发会有对应的类
        Map<String, Object> mapObj = new HashMap<>();
        mapObj.put("班级", teacher.getClazz());
        mapObj.put("老师", teacher.getName());
        mapObj.put("学生人数", stuCount);
        mapObj.put("班级总分数", totalScore);
        mapObj.put("班级平均分", averageScore);
        return mapObj;
    }
}