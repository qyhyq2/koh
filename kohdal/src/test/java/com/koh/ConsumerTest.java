package com.koh;

import com.alibaba.dubbo.config.annotation.Reference;
import com.koh.thrift.test.TestService;
import org.junit.Before;
import org.junit.Test;

public class ConsumerTest extends AbstractServiceTest {

    @Reference(loadbalance = "dubboRandom")
    private TestService.Iface testService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() throws Exception {
        System.out.println("dubbo RPC testing => ");

        int max = 50000;
        long start = System.currentTimeMillis();

        for (int i = 0; i < max; i++) {
            testService.getById((byte) 1);
        }

        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;

        System.out.println(String.format("%d次RPC调用(dubbo协议),共耗时%d毫秒,平均%f/秒", max, elapsedMilliseconds, max / (elapsedMilliseconds / 1000.0F)));
    }

}