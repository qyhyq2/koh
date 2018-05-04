package com.koh.server;

import com.koh.thrift.TestService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by qianyuhang on 2018/5/2.
 */
@Service
public class TestServer {

    @Autowired
    private TestService.Iface testService;

    @PostConstruct
    private void init() throws TTransportException {
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(9090);
        TServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(serverTransport).processor(new TestService.Processor(testService)));

        // Use this for a multithreaded server
        // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

        System.out.println("Starting the simple server...");
        server.serve();
    }
}
