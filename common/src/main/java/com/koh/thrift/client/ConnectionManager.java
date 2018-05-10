package com.koh.thrift.client;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 连接池管理
 */
//@Service
public class ConnectionManager {

    /** 日志记录器 */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** 保存local对象 */
    ThreadLocal<TTransport> socketThreadSafe = new ThreadLocal<>();

    /** 连接提供池 */
    @Resource
    private ConnectionProvider connectionProvider;

    public TTransport currentSocket() {
        return socketThreadSafe.get();
    }
    public void close() {
        connectionProvider.returnCon(socketThreadSafe.get());
        socketThreadSafe.remove();
    }
    public TTransport getSocket() {
        TTransport transport = null;
        try {
            transport = connectionProvider.getConnection();
            socketThreadSafe.set(transport);
            return transport;
        } catch (Exception e) {
            logger.error("error ConnectionManager.invoke()", e);
        }
        return transport;
    }
}
