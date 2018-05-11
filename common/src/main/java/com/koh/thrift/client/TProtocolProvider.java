package com.koh.thrift.client;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TProtocolProvider implements ConnectionProvider {
    private static final Logger log = LoggerFactory.getLogger(TProtocolProvider.class);

    private static final int MIN_CONN = 1;
    private static final int MAX_CONN = 1000;
    private static final int TIMEOUT = 5000;

    private final GenericKeyedObjectPool<ThriftServerInfo, TProtocol> connections;

    /**
     * 构造默认Thrift连接池实现
     *
     * @param config
     */
    public TProtocolProvider(GenericKeyedObjectPoolConfig config) {
        connections = new GenericKeyedObjectPool<>(new TProtocolFactory(true), config);
    }


    /**
     * 懒构造类
     */
    private static class LazyHolder {
        private static final TProtocolProvider INSTANCE;

        static {
            GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
            config.setMaxTotal(MAX_CONN);
            config.setMaxTotalPerKey(MAX_CONN);
            config.setMaxIdlePerKey(MAX_CONN);
            config.setMinIdlePerKey(MIN_CONN);
            config.setTestOnBorrow(true);
            config.setMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(1));
            config.setSoftMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(1));
            config.setJmxEnabled(false);

            INSTANCE = new TProtocolProvider(config);
        }
    }

    public static final TProtocolProvider getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public TProtocol getConnection(ThriftServerInfo thriftServerInfo) {
        try {
            return connections.borrowObject(thriftServerInfo);
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error("fail to get connection for {} error:'{}'", thriftServerInfo, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void returnConnection(ThriftServerInfo thriftServerInfo, TProtocol transport) {
        connections.returnObject(thriftServerInfo, transport);
    }

    @Override
    public void returnBrokenConnection(ThriftServerInfo thriftServerInfo, TProtocol transport) {
        try {
            connections.invalidateObject(thriftServerInfo, transport);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("fail to invalid object:{},{}", thriftServerInfo, e);
                throw new RuntimeException(e);
            }

        }
    }
}