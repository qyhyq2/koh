package com.koh.thrift.support.thrift;

/**
 * @author qianyuhang
 */
public class ThriftServerConfigItem {
    String name;

    int port;

    int threads;

    public ThriftServerConfigItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }
}
