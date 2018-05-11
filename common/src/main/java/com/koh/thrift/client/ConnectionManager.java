package com.koh.thrift.client;

import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 连接池管理
 */
//@Service
public class ConnectionManager {

    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 保存local对象
     */
    ThreadLocal<TProtocol> socketThreadSafe = new ThreadLocal<>();

    /**
     * 连接提供池
     */
    @Resource
    private ConnectionProvider connectionProvider;

    public TProtocol currentSocket() {
        return socketThreadSafe.get();
    }

    public void close(ThriftServerInfo serverInfo) {
        connectionProvider.returnConnection(serverInfo, socketThreadSafe.get());
        socketThreadSafe.remove();
    }

    public TProtocol getSocket(ThriftServerInfo serverInfo) {
        TProtocol transport = null;
        try {
            transport = connectionProvider.getConnection(serverInfo);
            socketThreadSafe.set(transport);
            return transport;
        } catch (Exception e) {
            logger.error("error ConnectionManager.invoke()", e);
        }
        return transport;
    }
}
