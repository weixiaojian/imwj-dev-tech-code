package com.imwj.design;

import java.util.List;

/**
 * 抽奖控制类
 * @author wj
 * @create 2023-05-22 15:38
 */
public class DrawControl {

    private IDraw draw;

    public List<BetUser> doDraw(IDraw draw, List<BetUser> betUserList, int count) {
        return draw.prize(betUserList, count);
    }
}
