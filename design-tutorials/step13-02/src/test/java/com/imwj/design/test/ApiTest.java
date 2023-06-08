package com.imwj.design.test;

import com.imwj.design.XiaoEr;
import com.imwj.design.cook.impl.GuangDongCook;
import com.imwj.design.cook.impl.JiangSuCook;
import com.imwj.design.cuisine.ICuisine;
import com.imwj.design.cuisine.impl.GuangDoneCuisine;
import com.imwj.design.cuisine.impl.JiangSuCuisine;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-06-08 17:31
 */
public class ApiTest {

    @Test
    public void test_xiaoEr(){
        // 菜系 + 厨师；广东（粤菜）、江苏（苏菜）、山东（鲁菜）、四川（川菜）
        ICuisine guangDoneCuisine = new GuangDoneCuisine(new GuangDongCook());
        JiangSuCuisine jiangSuCuisine = new JiangSuCuisine(new JiangSuCook());

        // 点单
        XiaoEr xiaoEr = new XiaoEr();
        xiaoEr.order(guangDoneCuisine);
        xiaoEr.order(jiangSuCuisine);

        xiaoEr.placeOrder();

    }
}
