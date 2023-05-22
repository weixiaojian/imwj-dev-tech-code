package com.imwj.design.impl;

import com.imwj.design.ISkillInvisible;
import com.imwj.design.ISkillSilent;
import com.imwj.design.ISkillVertigo;

/**
 * 廉颇功能类
 * @author wj
 * @create 2023-05-22 15:18
 */
public class HeroLianPo implements ISkillInvisible, ISkillSilent, ISkillVertigo {

    @Override
    public void doInvisible() {
        System.out.println("廉颇的隐身技能");
    }

    @Override
    public void doSilent() {
        System.out.println("廉颇的沉默技能");
    }

    @Override
    public void doVertigo() {
        System.out.println("廉颇的眩晕技能");
    }
}