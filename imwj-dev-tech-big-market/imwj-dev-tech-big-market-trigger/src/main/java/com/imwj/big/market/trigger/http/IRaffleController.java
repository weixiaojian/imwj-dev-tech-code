package com.imwj.big.market.trigger.http;

import com.alibaba.fastjson2.JSON;
import com.imwj.big.market.api.IRaffleService;
import com.imwj.big.market.api.dto.RaffleAwardListRequestDTO;
import com.imwj.big.market.api.dto.RaffleAwardListResponseDTO;
import com.imwj.big.market.api.dto.RaffleRequestDTO;
import com.imwj.big.market.api.dto.RaffleResponseDTO;
import com.imwj.big.market.domain.strategy.model.entity.RaffleAwardEntity;
import com.imwj.big.market.domain.strategy.model.entity.RaffleFactorEntity;
import com.imwj.big.market.domain.strategy.model.entity.StrategyAwardEntity;
import com.imwj.big.market.domain.strategy.service.IRaffleAward;
import com.imwj.big.market.domain.strategy.service.IRaffleStrategy;
import com.imwj.big.market.domain.strategy.service.armory.IStrategyArmory;
import com.imwj.big.market.types.enums.ResponseCode;
import com.imwj.big.market.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wj
 * @create 2024-05-17 17:17
 * @description 抽奖Controller
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/")
public class IRaffleController implements IRaffleService {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IRaffleAward raffleAward;
    @Resource
    private IRaffleStrategy raffleStrategy;
    /**
     * 策略装配接口
     * @param strategyId 策略id
     * @return
     */
    @RequestMapping(value = "strategy_armory", method = RequestMethod.GET)
    @Override
    public Response<Boolean> strategyArmory(Long strategyId) {
        try {
            log.info("抽奖策略装配开始：{}", strategyId);
            boolean armoryStatus = strategyArmory.assembleLotteryStrategy(strategyId);
            log.info("抽奖策略装配完成：{}", armoryStatus);
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(armoryStatus)
                    .build();
        } catch (Exception e) {
            log.error("抽奖策略装配失败：{}", strategyId);
            e.printStackTrace();
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 根据策略id查询奖品列表
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "query_raffle_award_list", method = RequestMethod.POST)
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardListRequestDTO requestDTO) {
        try{
            log.info("根据策略id查询奖品列表开始：{}", JSON.toJSONString(requestDTO));
            List<StrategyAwardEntity> strategyAwardEntities = raffleAward.queryRaffleStrategyAwardList(requestDTO.getStrategyId());
            log.info("抽根据策略id查询奖品列表完成：{}", JSON.toJSONString(strategyAwardEntities));

            // 使用 Spring BeanUtils 进行对象复制
            List<RaffleAwardListResponseDTO> raffleAwardListResponseDTOs = strategyAwardEntities.stream()
                    .map(entity -> {
                        RaffleAwardListResponseDTO dto = new RaffleAwardListResponseDTO();
                        BeanUtils.copyProperties(entity, dto);
                        dto.setAwardSubtitle(entity.getAwardSubTitle());
                        return dto;
                    })
                    .collect(Collectors.toList());
            // 返回结果
            return Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardListResponseDTOs)
                    .build();
        }catch (Exception e){
            log.error("根据策略id查询奖品列表失败：{}", JSON.toJSONString(requestDTO));
            e.printStackTrace();
            return Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 抽奖接口
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "random_raffle", method = RequestMethod.POST)
    @Override
    public Response<RaffleResponseDTO> randomRaffle(@RequestBody RaffleRequestDTO requestDTO) {
        try{
            log.info("抽奖接口开始：{}", JSON.toJSONString(requestDTO));
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId(requestDTO.getUserId())
                    .strategyId(requestDTO.getStrategyId())
                    .build();
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
            log.info("抽奖接口完成：{}", JSON.toJSONString(raffleAwardEntity));

            // 使用 Spring BeanUtils 进行对象复制
            RaffleResponseDTO raffleResponseDTO = RaffleResponseDTO.builder()
                    .awardId(raffleAwardEntity.getAwardId())
                    .awardIndex(raffleAwardEntity.getSort())
                    .build();

            // 返回结果
            return Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleResponseDTO)
                    .build();
        }catch (Exception e){
            log.error("抽奖接口失败：{}", JSON.toJSONString(requestDTO));
            e.printStackTrace();
            return Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
