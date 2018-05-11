package com.koh.thrift.client;

import com.koh.thrift.support.Thrift2Protocol;
import com.koh.thrift.support.ThriftConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(ThriftConstant.CLIENT_CONFIG_PREFIX)
@Component
@ConditionalOnClass(Thrift2Protocol.class)
public class ThriftClientConfig {
    public static final int DEFAULT_TIMEOUT = 5000;
    private ThriftClientPoolConfig pool;
    private int timeout;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public ThriftClientPoolConfig getPool() {
        return pool;
    }

    public void setPool(ThriftClientPoolConfig pool) {
        this.pool = pool;
    }

}
