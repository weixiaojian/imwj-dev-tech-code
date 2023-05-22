package com.imwj.design;

import java.util.List;

/**
 * 抽奖接口
 * @author wj
 * @create 2023-05-22 15:33
 */
public interface IDraw {

    /**
     * 抽奖操作
     * @param list
     * @param count
     * @return
     */
    List<BetUser> prize(List<BetUser> list, int count);
}
