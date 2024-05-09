package com.imwj.big.market.domain.model.valobj;

import com.imwj.big.market.domain.service.rule.filter.factory.DefaultLogicFactory;
import com.imwj.big.market.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wj
 * @create 2024-05-07 11:23
 * @description 抽奖策略规则值对象；值对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModeVo {

    private String ruleModes;

    public String[] raffleCenterRuleModeList(){
        List<String> ruleModeList = new ArrayList<>();

        String[] ruleModeValues = ruleModes.split(Constants.SPLIT);
        for(String ruleModeValue : ruleModeValues){
            if(DefaultLogicFactory.LogicModel.isCenter(ruleModeValue)){
                ruleModeList.add(ruleModeValue);
            }
        }
        return ruleModeList.toArray(new String[0]);
    }

}
