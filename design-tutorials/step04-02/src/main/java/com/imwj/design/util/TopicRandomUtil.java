package com.imwj.design.util;

import java.util.*;

/**
 * @author wj
 * @create 2023-05-30 11:15
 */
public class TopicRandomUtil {

    /**
     * 乱序Map元素，记录对应答案key
     * @param option
     * @param key
     * @return
     */
    public static Topic random(Map<String, String> option, String key){
        Set<String> keySet = option.keySet();
        ArrayList<String> keyList = new ArrayList<String>(keySet);
        Collections.shuffle(keyList);

        HashMap<String, String> optionNew = new HashMap<>();
        int idx = 0;
        String keyNew = "";
        for(String next : keySet){
            String randomKey = keyList.get(idx++);
            if(key.equals(next)){
                keyNew = randomKey;
            }
            optionNew.put(randomKey, option.get(next));
        }
        return new Topic(optionNew, keyNew);
    }
}
