package com.imwj.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wj
 * @create 2023-04-24 17:22
 */
public class Main {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        // 系统时间戳
        System.out.println(System.currentTimeMillis());

        // 当前日期
        System.out.println(new Date());

        // 日期格式化
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS" );
        Date d= new Date();
        String str = sdf.format(d);
        System.out.println("当前时间通过 yyyy-MM-dd HH:mm:ss SSS 格式化后的输出: "+str);

        // 日历操作

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        // 当前日期
        System.out.println("当前日期：\t" + sdf.format(c.getTime()));
        // 下个月的今天
        c.setTime(now);
        c.add(Calendar.MONTH, 1);
        System.out.println("下个月的今天:\t" + sdf.format(c.getTime()));
    }

}
