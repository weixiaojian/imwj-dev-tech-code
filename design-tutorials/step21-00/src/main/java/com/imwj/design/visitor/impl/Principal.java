package com.imwj.design.visitor.impl;

import com.imwj.design.user.impl.Student;
import com.imwj.design.user.impl.Teacher;
import com.imwj.design.visitor.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 访问者 校长
 * @author wj
 * @create 2023-06-26 18:05
 */
public class Principal implements Visitor {
    private Logger logger = LoggerFactory.getLogger(Principal.class);

    public void visit(Student student) {
        logger.info("学生信息 姓名：{} 班级：{}", student.name, student.clazz);
    }

    public void visit(Teacher teacher) {
        logger.info("学生信息 姓名：{} 班级：{} 升学率：{}", teacher.name, teacher.clazz, teacher.entranceRatio());
    }

}
