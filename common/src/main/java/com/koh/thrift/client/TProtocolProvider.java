package com.koh.thrift.client;

import com.koh.thrift.support.Thrift2Protocol;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnClass(Thrift2Protocol.class)
public class TProtocolProvider implements ConnectionProvider {
    private static final Logger log = LoggerFactory.getLogger(TProtocolProvider.class);

    private static TProtocolProvider INSTANCE;

    private final GenericKeyedObjectPool<ThriftServerInfo, TProtocol> connections;

    /**
     * 构造默认Thrift连接池实现
     */
    public TProtocolProvider(ThriftClientConfig clientConfig) {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        ThriftClientPoolConfig pool = clientConfig.getPool();
        int maxTotal = pool.getMaxTotal() > 0 ? pool.getMaxTotal() : ThriftClientPoolConfig.DEFAULT_MAX_CONN;
        config.setMaxTotal(maxTotal);
        config.setMaxTotalPerKey(maxTotal);
        config.setMaxIdlePerKey(pool.getMaxIdle() > 0 ? pool.getMaxIdle() : ThriftClientPoolConfig.DEFAULT_MAX_IDLE);
        config.setMinIdlePerKey(pool.getMinIdle() > 0 ? pool.getMinIdle() : ThriftClientPoolConfig.DEFAULT_MIN_IDLE);
        config.setTestOnBorrow(true);
        config.setMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(1));
        config.setSoftMinEvictableIdleTimeMillis(TimeUnit.MINUTES.toMillis(1));
        config.setJmxEnabled(false);
        config.setMaxWaitMillis(pool.getMaxWait() > 0 ? pool.getMaxWait() : ThriftClientPoolConfig.DEFAULT_MAX_WAIT);
        int timeout = clientConfig.getTimeout() > 0 ? clientConfig.getTimeout() : ThriftClientConfig.DEFAULT_TIMEOUT;
        connections = new GenericKeyedObjectPool<>(new TProtocolFactory(true, timeout), config);
    }


    @PostConstruct
    private void post() {
        INSTANCE = this;
    }

    public static final TProtocolProvider getInstance() {
        return INSTANCE;
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
    public void returnConnection(ThriftServerInfo thriftServerInfo, TProtocol tProtocol) {
        connections.returnObject(thriftServerInfo, tProtocol);
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