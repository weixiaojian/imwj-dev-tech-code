package com.imwj.design.test;

import com.imwj.design.CashCard;
import com.imwj.design.CreditCard;
import com.imwj.designTo.CashCardTo;
import com.imwj.designTo.CreditCardTo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author wj
 * @create 2023-05-22 11:41
 */
public class ApiTestTo {

    private Logger logger = LoggerFactory.getLogger(ApiTestTo.class);

    @Test
    public void test_bankCard() {
        logger.info("里氏替换前，CashCard类：");
        CashCardTo bankCard = new CashCardTo("6214567800989876", "2022-03-05");
        // 提现
        bankCard.withdrawal("100001", new BigDecimal(100));
        // 储蓄
        bankCard.recharge("100001", new BigDecimal(100));

        logger.info("里氏替换后，CreditCard类：");
        CreditCardTo creditCard = new CreditCardTo("6214567800989876", "2022-03-05");
        // 提现
        creditCard.withdrawal("100001", new BigDecimal(1000000));
        // 储蓄
        creditCard.recharge("100001", new BigDecimal(100));
    }

    @Test
    public void test_CreditCard(){
        CreditCardTo creditCard = new CreditCardTo("6214567800989876", "2022-03-05");
        // 支付，贷款
        creditCard.loan("100001", new BigDecimal(100));
        // 还款
        creditCard.repayment("100001", new BigDecimal(100));
    }

}
