package com.imwj.desgin.test;

import com.imwj.design.service.ICalculationArea;
import com.imwj.design.service.impl.CalculationAreaExt;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-05-22 11:18
 */
public class ApiTest {

    @Test
    public void test_CalculationAreaExt(){
        ICalculationArea area = new CalculationAreaExt();
        double circular = area.circular(10);
        System.out.println(circular);
    }

}
