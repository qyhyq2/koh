package com.koh.thrift;

import com.alibaba.dubbo.config.annotation.Reference;
import com.koh.thrift.test.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConsumerBean {
    @Reference
    private TestService.Iface testService;


//    @PostConstruct
    public void test() throws Exception {
        System.out.println(testService.echo("2"));
    }
}
