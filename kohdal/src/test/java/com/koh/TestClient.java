package com.koh;

import com.koh.thrift.test.TestService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by qianyuhang on 2018/5/2.
 */
public class TestClient {
    private TestService.Client testService;
    private TBinaryProtocol protocol;
    private TSocket transport;
    private String host;
    private int port;

    public TestClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.init();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void init() {
        transport = new TSocket(host, port);
        protocol = new TBinaryProtocol(transport);
        testService = new TestService.Client(protocol);
    }

    public TestService.Client getTestService() {
        return testService;
    }

    public void open() throws TTransportException {
        transport.open();
    }

    public void close() {
        transport.close();
    }

    public static void main(String[] args) {
        TestClient client = new TestClient("localhost", 30880);
        try {
            client.open();
            boolean isExists = client.getTestService().exists("12345");
            System.out.println("result:" + isExists);
        } catch (Exception e) {
        } finally {
            client.close();
        }
    }
}
