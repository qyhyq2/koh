package com.koh.thrift.client;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TProtocolFactory extends BaseKeyedPooledObjectFactory<ThriftServerInfo, TProtocol> {
    private boolean keepAlive;
    private int timeout;

    public TProtocolFactory(boolean keepAlive, int timeout) {
        this.keepAlive = keepAlive;
        this.timeout = timeout;
    }

    @Override
    public TProtocol create(ThriftServerInfo key) throws Exception {
        TSocket tSocket = new TSocket(key.getHost(), key.getPort());
        tSocket.setTimeout(timeout);
        TTransport tTransport = new TFramedTransport(tSocket);
        tTransport.open();
        return new TCompactProtocol(tTransport);
    }

    @Override
    public PooledObject<TProtocol> wrap(TProtocol protocol) {
        return new DefaultPooledObject<>(protocol);
    }

    /**
     * 对象钝化(即：从激活状态转入非激活状态，returnObject时触发）
     */
    @Override
    public void passivateObject(ThriftServerInfo key, PooledObject<TProtocol> pooledObject) throws TTransportException {
        if (!keepAlive) {
            pooledObject.getObject().getTransport().flush();
            pooledObject.getObject().getTransport().close();
        }
    }

    /**
     * 对象激活(borrowObject时触发）
     */
    @Override
    public void activateObject(ThriftServerInfo key, PooledObject<TProtocol> pooledObject) throws TTransportException {
        if (!pooledObject.getObject().getTransport().isOpen()) {
            pooledObject.getObject().getTransport().open();
        }
    }


    /**
     * 对象销毁(clear时会触发）
     */
    @Override
    public void destroyObject(ThriftServerInfo key, PooledObject<TProtocol> pooledObject) throws TTransportException {
        if (pooledObject.getObject() != null && pooledObject.getObject().getTransport().isOpen()) {
            pooledObject.getObject().getTransport().flush();
            pooledObject.getObject().getTransport().close();
        }
        pooledObject.markAbandoned();
    }


    /**
     * 验证对象有效性
     */
    @Override
    public boolean validateObject(ThriftServerInfo key, PooledObject<TProtocol> p) {
        if (p.getObject() != null) {
            if (p.getObject().getTransport().isOpen()) {
                return true;
            }
            try {
                p.getObject().getTransport().open();
                return true;
            } catch (TTransportException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
