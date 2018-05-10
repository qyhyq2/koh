package com.koh.thrift.client;

import org.apache.thrift.transport.TTransport;

/**
 * 连接池接口
 */
public interface ConnectionProvider {
    /**
     * 取链接池中的一个链接
     *
     * @return TSocket
     */
    TTransport getConnection();

    /**
     * 返回链接
     *
     * @param socket
     */
    void returnCon(TTransport socket);
}