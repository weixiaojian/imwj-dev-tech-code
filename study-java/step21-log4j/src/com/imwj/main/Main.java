package com.imwj.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author wj
 * @create 2023-04-27 15:33
 */
public class Main {

    /**
     * 基于类的名称获取日志对象
     */
    static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        // 进行默认配置
        BasicConfigurator.configure();
        // 设定日志输出级别，超过这个级别才进行输出
        logger.setLevel(Level.DEBUG);
        // 进行不同级别的日志输出
        logger.trace("跟踪信息");
        logger.debug("调试信息");
        logger.info("输出信息");
        Thread.sleep(1000);
        logger.warn("警告信息");
        logger.error("错误信息");
        logger.fatal("致命信息");

        // 文件输出
        PropertyConfigurator.configure("E:\\idea-work\\imwj\\study-code\\study-java\\step21-log4j\\src\\log4j.properties");
        for (int i = 0; i < 5000; i++) {
            logger.trace("跟踪信息");
            logger.debug("调试信息");
            logger.info("输出信息");
            logger.warn("警告信息");
            logger.error("错误信息");
            logger.fatal("致命信息");
        }
    }

}
