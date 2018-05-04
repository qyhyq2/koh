package com.koh;


import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws InterruptedException, TTransportException {
        ApplicationContext ctx = SpringApplication.run(MainApp.class, args);
        logger.info("spring boot 启动环境和配置信息如下：" + ctx.getEnvironment());


    }
}
