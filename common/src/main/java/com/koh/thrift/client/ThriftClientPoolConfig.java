package com.koh.thrift.client;

public class ThriftClientPoolConfig {
    public static final int DEFAULT_MIN_IDLE = 0;
    public static final int DEFAULT_MAX_CONN = 30;
    public static final int DEFAULT_MAX_IDLE = 30;
    public static final int DEFAULT_MAX_WAIT = 5000;

    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWait;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
}
