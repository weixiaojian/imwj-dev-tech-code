package com.imwj.design.test;

import com.imwj.design.QuestionBankController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wj
 * @create 2023-05-30 11:06
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private QuestionBankController questionBankController = new QuestionBankController();

    @Test
    public void test_api(){
        System.out.println(questionBankController.createPaper("花花", "100001"));;
        System.out.println(questionBankController.createPaper("萌兰", "100002"));
        System.out.println(questionBankController.createPaper("大庆", "100003"));
    }

}
