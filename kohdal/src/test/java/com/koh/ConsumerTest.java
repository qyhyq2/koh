package com.koh;

import com.alibaba.dubbo.config.annotation.Reference;
import com.koh.thrift.second.SecondService;
import com.koh.thrift.test.TestService;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerTest extends AbstractServiceTest {

    //    @Reference(loadbalance = "dubboRandom")
    @Reference
    private TestService.Iface testService;

    @Reference
    private SecondService.Iface secondService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() throws Exception {
        System.out.println(testService.echo("2"));
    }

    @Test
    public void ga() throws Exception {
        System.out.println(secondService.add(1, 2));
    }


    @Test
    public void testPerm() throws Exception {
        int max = 5000;
        System.out.println("dubbo RPC testing => ");
        ExecutorService threadPool = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(max);

        long start = System.currentTimeMillis();

        for (int i = 0; i < max; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                try {
                    testService.getById(finalI);
                    latch.countDown();
                } catch (TException e) {
                    e.printStackTrace();
                }
            });
        }

        latch.await();

        long end = System.currentTimeMillis();
        long elapsedMilliseconds = end - start;

        System.out.println(String.format("%d次RPC调用,共耗时%d毫秒,平均%f ms/次", max, elapsedMilliseconds, elapsedMilliseconds / (double) max));
    }

}