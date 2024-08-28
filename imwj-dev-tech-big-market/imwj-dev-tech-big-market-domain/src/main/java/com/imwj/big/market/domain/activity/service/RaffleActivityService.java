package com.imwj.big.market.domain.activity.service;

import com.imwj.big.market.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author wj
 * @create 2024-08-28 15:05
 * @description 抽奖活动服务
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity {

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}
