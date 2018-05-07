package com.koh.thrift.support;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.Constructor;

public class Thrift2Protocol extends AbstractProxyProtocol {
    private static final String iFace = "$Iface";
    private static final String client = "$Client";
    private static final String processor = "$Processor";
    public static final int DEFAULT_PORT = 30880;
    private static final Logger logger = LoggerFactory.getLogger(Thrift2Protocol.class);
    private TSocket tSocket;

    @Override
    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    @Override
    protected <T> Runnable doExport(T impl, Class<T> type, URL url)
            throws RpcException {

        logger.info("impl => " + impl.getClass());
        logger.info("type => " + type.getName());
        logger.info("url => " + url);

        TProcessor tprocessor;
        TThreadedSelectorServer.Args tArgs = null;
        String typeName = type.getName();
        TNonblockingServerSocket transport;
        if (typeName.endsWith(iFace)) {
            String processorClsName = typeName.substring(0, typeName.indexOf(iFace)) + processor;
            try {
                Class<?> clazz = Class.forName(processorClsName);
                Constructor constructor = clazz.getConstructor(type);
                try {
                    tprocessor = (TProcessor) constructor.newInstance(impl);
                    transport = new TNonblockingServerSocket(url.getPort());
                    tArgs = new TThreadedSelectorServer.Args(transport);
                    tArgs.processor(tprocessor);
                    tArgs.transportFactory(new TFramedTransport.Factory());
                    tArgs.protocolFactory(new TBinaryProtocol.Factory());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RpcException("Fail to create thrift server(" + url + ") : " + e.getMessage(), e);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RpcException("Fail to create thrift server(" + url + ") : " + e.getMessage(), e);
            }
        }

        if (tArgs == null) {
            logger.error("Fail to create thrift server(" + url + ") due to null args");
            throw new RpcException("Fail to create thrift server(" + url + ") due to null args");
        }
        final TServer thriftServer = new TThreadedSelectorServer(tArgs);

        new Thread(() -> {
            logger.info("Start Thrift Server");
            thriftServer.serve();
            logger.info("Thrift server started.");
        }).start();

        return () -> {
            try {
                logger.info("Close Thrift Server");
                thriftServer.stop();
            } catch (Throwable e) {
                logger.warn(e.getMessage(), e);
            }
        };
    }

    @Override
    protected <T> T doRefer(Class<T> type, URL url) throws RpcException {

        logger.info("type => " + type.getName());
        logger.info("url => " + url);

        try {
            TTransport transport;
            TProtocol protocol;
            T thriftClient = null;

            String typeName = type.getName();
            if (typeName.endsWith(iFace)) {
                String clientClsName = typeName.substring(0, typeName.indexOf(iFace)) + client;
                Class<?> clazz = Class.forName(clientClsName);
                Constructor constructor = clazz.getConstructor(TProtocol.class);
                try {
                    tSocket = new TSocket(url.getHost(), url.getPort());
                    transport = new TFramedTransport(tSocket);
                    protocol = new TBinaryProtocol(transport);
                    thriftClient = (T) constructor.newInstance(protocol);
                    transport.open();
                    logger.info("thrift client opened for service(" + url + ")");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RpcException("Fail to create remoting client:" + e.getMessage(), e);
                }
            }
            return thriftClient;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcException("Fail to create remoting client for service(" + url + "): " + e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        tSocket.close();
    }
}
