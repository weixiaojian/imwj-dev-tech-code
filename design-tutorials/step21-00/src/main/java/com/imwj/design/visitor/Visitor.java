package com.imwj.design.visitor;

import com.imwj.design.user.impl.Student;
import com.imwj.design.user.impl.Teacher;

/**
 * 访问者接口
 * @author wj
 * @create 2023-06-26 18:02
 */
public interface Visitor {

    // 访问学生信息
    void visit(Student student);

    // 访问老师信息
    void visit(Teacher teacher);

}
