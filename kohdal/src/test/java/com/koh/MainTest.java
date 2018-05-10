package com.koh;

import com.alibaba.dubbo.rpc.RpcException;
import com.koh.thrift.test.TestService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {
    public static TestService.Iface thriftClient;
    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    public static void main(String[] args) {
        TTransport transport;
        TProtocol protocol;
        try {
            TSocket tSocket = new TSocket("192.168.171.38", 9090);
            transport = new TFramedTransport(tSocket);
            protocol = new TBinaryProtocol(transport);
            thriftClient = new TestService.Client(protocol);
            transport.open();

            int max = 10;
            System.out.println("dubbo RPC testing => ");
            CountDownLatch latch = new CountDownLatch(max);

            long start = System.currentTimeMillis();

            for (int i = 0; i < max; i++) {
                executorService.submit(() -> {
                    try {
                        System.out.println(thriftClient.echo("1"));
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
        } catch (Exception e) {
            throw new RpcException("Fail to create remoting client:" + e.getMessage(), e);
        }
    }
}
