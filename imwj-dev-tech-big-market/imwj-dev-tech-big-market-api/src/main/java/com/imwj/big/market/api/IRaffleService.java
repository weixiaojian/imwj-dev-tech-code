package com.imwj.big.market.api;

import com.imwj.big.market.api.dto.RaffleAwardListRequestDTO;
import com.imwj.big.market.api.dto.RaffleAwardListResponseDTO;
import com.imwj.big.market.api.dto.RaffleRequestDTO;
import com.imwj.big.market.api.dto.RaffleResponseDTO;
import com.imwj.big.market.types.model.Response;

import java.util.List;

/**
 * @author wj
 * @create 2024-05-17 16:54
 * @description 抽奖服务接口
 */
public interface IRaffleService {

    /**
     * 策略装配接口
     * @param strategyId 策略id
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询奖品配置列表
     * @return
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     * @param requestDTO
     * @return
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);
}
