package com.imwj.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 随机筹集
 * @author wj
 * @create 2023-05-22 15:33
 */
public class DrawRandom implements IDraw{

    @Override
    public List<BetUser> prize(List<BetUser> list, int count) {
        // 集合数量很小直接返回
        if (list.size() <= count) return list;
        // 乱序集合
        Collections.shuffle(list);
        // 取出指定数量的中奖用户
        List<BetUser> prizeList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            prizeList.add(list.get(i));
        }
        return prizeList;
    }
}
