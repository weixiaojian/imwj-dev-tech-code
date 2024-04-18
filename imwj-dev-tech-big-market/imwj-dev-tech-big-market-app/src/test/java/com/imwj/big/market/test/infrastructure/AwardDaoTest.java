package com.imwj.big.market.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.imwj.big.market.infrastructure.persistent.dao.IAwardDao;
import com.imwj.big.market.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wj
 * @create 2024-04-18 17:31
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {

    @Resource
    private IAwardDao awardDao;


    @Test
    public void test_awardDao(){
        List<Award> awards = awardDao.queryAwardList();
        log.info("查询到的数据：{}", JSON.toJSONString(awards));
    }

}
