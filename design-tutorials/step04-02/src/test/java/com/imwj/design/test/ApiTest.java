package com.imwj.design.test;

import com.imwj.design.QuestionBankController;
import org.junit.Test;

/**
 * @author wj
 * @create 2023-05-30 11:36
 */
public class ApiTest {

    @Test
    public void test_QuestionBank() throws CloneNotSupportedException {
        QuestionBankController questionBankController = new QuestionBankController();
        System.out.println(questionBankController.createPaper("花花", "1000001921032"));
        System.out.println(questionBankController.createPaper("萌兰", "1000001921051"));
        System.out.println(questionBankController.createPaper("大庆", "1000001921987"));
    }
}
