package com.imwj.big.market.trigger.job;

import com.imwj.big.market.domain.strategy.model.valobj.StrategyAwardStockKeyVo;
import com.imwj.big.market.domain.strategy.service.IRaffleStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wj
 * @create 2024-05-15 15:20
 * @description 更奖品表库存定时任务
 */
@Slf4j
@Component("updateAwardStockJob")
public class UpdateAwardStockJob {

    @Resource
    private IRaffleStock raffleStock;

    /**
     * 从redis中获取延迟队列更新数据库库存
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void exec(){
        try {
            StrategyAwardStockKeyVo strategyAwardStockKeyVo = raffleStock.takeQueueValue();
            if(strategyAwardStockKeyVo == null) return;
            log.info("UpdateAwardStockJob定时任务，开始 strategyId:{} awardId:{}", strategyAwardStockKeyVo.getStrategyId(), strategyAwardStockKeyVo.getAwardId());
            raffleStock.updateStrategyAwardStock(strategyAwardStockKeyVo.getStrategyId(), strategyAwardStockKeyVo.getAwardId());
            log.info("UpdateAwardStockJob定时任务，结束");
        } catch (Exception e) {
            log.error("UpdateAwardStockJob定时任务，异常", e);
        }
    }
}
