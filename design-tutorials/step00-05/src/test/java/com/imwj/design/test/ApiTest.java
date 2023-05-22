package com.imwj.design.test;

import com.imwj.design.impl.HeroHouYi;
import com.imwj.design.impl.HeroLianPo;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-05-22 15:19
 */
public class ApiTest {

    @Test
    public void test_ISkill(){
        // 后裔
        HeroHouYi heroHouYi = new HeroHouYi();
        heroHouYi.doArchery();

        // 廉颇
        HeroLianPo heroLianPo = new HeroLianPo();
        heroLianPo.doInvisible();
    }

}
