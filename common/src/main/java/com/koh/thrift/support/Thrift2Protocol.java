package com.koh.thrift.support;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.koh.thrift.support.ThriftConstant.*;

public class Thrift2Protocol extends AbstractProxyProtocol {
    public static final int DEFAULT_PORT = 30880;
    public static final int DEFAULT_THREADS = 100;
    private static final Logger logger = LoggerFactory.getLogger(Thrift2Protocol.class);

    private final Map<String, TServer> serverMap = new ConcurrentHashMap<>();
    private TSocket tSocket;

    @Override
    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    @Override
    protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
        logger.info("impl => " + impl.getClass());
        logger.info("type => " + type.getName());
        logger.info("url => " + url);

        String addr = getAddr(url);
        TServer thriftServer = serverMap.get(addr);
        if (thriftServer == null) {
            thriftServer = new TThreadedSelectorServer(buildArgs(impl, type, url));
            serverMap.put(addr, thriftServer);
        }

        TServer finalThriftServer = thriftServer;
        new Thread(() -> {
            logger.info("Start Thrift Server");
            finalThriftServer.serve();
            logger.info("Thrift server started.");
        }).start();

        return () -> {
            try {
                logger.info("Close Thrift Server");
                finalThriftServer.stop();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        };
    }

    private <T> TThreadedSelectorServer.Args buildArgs(T impl, Class<T> type, URL url) {
        TProcessor tprocessor;
        TThreadedSelectorServer.Args tArgs = null;
        String typeName = type.getName();
        TNonblockingServerSocket transport;
        if (typeName.endsWith(IFACE)) {
            String processorClsName = typeName.substring(0, typeName.indexOf(IFACE)) + PROCESSOR;
            try {
                Class<?> clazz = Class.forName(processorClsName);
                Constructor constructor = clazz.getConstructor(type);
                try {
                    tprocessor = (TProcessor) constructor.newInstance(impl);
                    TNonblockingServerSocket.NonblockingAbstractServerSocketArgs socketArgs =
                            new TNonblockingServerSocket.NonblockingAbstractServerSocketArgs()
                                    .port(url.getPort())
                                    .clientTimeout(url.getParameter(Constants.TIMEOUT_KEY, 0));
                    transport = new TNonblockingServerSocket(socketArgs);
                    tArgs = new TThreadedSelectorServer.Args(transport);
                    tArgs.processor(tprocessor);
                    tArgs.transportFactory(new TFramedTransport.Factory());
                    tArgs.protocolFactory(new TCompactProtocol.Factory());
                    tArgs.selectorThreads(Runtime.getRuntime().availableProcessors());
                    tArgs.workerThreads(url.getParameter(Constants.THREADS_KEY, DEFAULT_THREADS));
                    tArgs.acceptPolicy(TThreadedSelectorServer.Args.AcceptPolicy.FAIR_ACCEPT);
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

        return tArgs;
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
            if (typeName.endsWith(IFACE)) {
                String clientClsName = typeName.substring(0, typeName.indexOf(IFACE)) + CLIENT;
                Class<?> clazz = Class.forName(clientClsName);
                Constructor constructor = clazz.getConstructor(TProtocol.class);
                try {
                    tSocket = new TSocket(url.getHost(), url.getPort());
                    transport = new TFramedTransport(tSocket);
                    protocol = new TCompactProtocol(transport);
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
