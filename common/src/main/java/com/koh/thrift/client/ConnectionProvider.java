package com.koh.thrift.client;

import org.apache.thrift.protocol.TProtocol;

/**
 * 连接池接口
 */
public interface ConnectionProvider {
    /**
     * 获取thrift连接
     *
     * @param thriftServerInfo
     * @return
     */
    TProtocol getConnection(ThriftServerInfo thriftServerInfo);

    /**
     * 释放返回连接
     *
     * @param thriftServerInfo
     * @param transport
     */
    void returnConnection(ThriftServerInfo thriftServerInfo, TProtocol transport);

    /**
     * 释放返回失败的连接
     *
     * @param thriftServerInfo
     * @param transport
     */
    void returnBrokenConnection(ThriftServerInfo thriftServerInfo, TProtocol transport);
}