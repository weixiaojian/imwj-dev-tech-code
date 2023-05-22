package com.imwj.design.impl;

import com.imwj.design.ISkillArchery;
import com.imwj.design.ISkillInvisible;
import com.imwj.design.ISkillSilent;

/**
 * 后裔功能类
 * @author wj
 * @create 2023-05-22 15:17
 */
public class HeroHouYi implements ISkillArchery, ISkillInvisible, ISkillSilent {

    @Override
    public void doArchery() {
        System.out.println("后裔的灼日之矢");
    }

    @Override
    public void doInvisible() {
        System.out.println("后裔的隐身技能");
    }

    @Override
    public void doSilent() {
        System.out.println("后裔的沉默技能");
    }

}
