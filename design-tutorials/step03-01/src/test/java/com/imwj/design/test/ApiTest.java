package com.imwj.design.test;

import com.imwj.design.DecorationPackageController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author wj
 * @create 2023-05-29 17:24
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private DecorationPackageController decorationPackageController = new DecorationPackageController();

    @Test
    public void test_Decoration(){
        String one = decorationPackageController.getMatterList(new BigDecimal(100), 1);
        System.out.println(one);

        String two = decorationPackageController.getMatterList(new BigDecimal(100), 2);
        System.out.println(two);

        String three = decorationPackageController.getMatterList(new BigDecimal(100), 3);
        System.out.println(three);
    }
}
