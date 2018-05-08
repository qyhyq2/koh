package com.koh;

import com.alibaba.dubbo.config.annotation.Reference;
import com.koh.thrift.TestService;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    @Test
    public void testPerm() throws Exception {
        int max = 10;
        System.out.println("dubbo RPC testing => ");
        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(max);

        long start = System.currentTimeMillis();

        for (int i = 0; i < max; i++) {
            threadPool.submit(() -> {
                try {
                    testService.getById((byte) 1);
                    latch.countDown();
                } catch (TException e) {
                    e.printStackTrace();
                }
            });
        }

        latch.await();

        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;

        System.out.println(String.format("%d次RPC调用,共耗时%d毫秒,平均%f/秒", max, elapsedMilliseconds, max / (elapsedMilliseconds / 1000.0F)));
    }

}