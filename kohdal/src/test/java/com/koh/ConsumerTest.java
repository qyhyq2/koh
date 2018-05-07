package com.koh;

import com.alibaba.dubbo.config.annotation.Reference;
import com.koh.thrift.TestService;
import org.junit.Before;
import org.junit.Test;

public class ConsumerTest extends AbstractServiceTest {

    @Reference
    private TestService.Iface testService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() throws Exception {
        System.out.println(testService.getById((byte) 1).name);
    }

}