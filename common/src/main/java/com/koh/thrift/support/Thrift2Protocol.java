package com.koh.thrift.support;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;
import com.koh.thrift.client.TProtocolProvider;
import com.koh.thrift.client.ThriftServerInfo;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.koh.thrift.support.ThriftConstant.*;

public class Thrift2Protocol extends AbstractProxyProtocol {
    private static final Logger log = LoggerFactory.getLogger(Thrift2Protocol.class);

    private final Map<String, TServer> serverMap = new ConcurrentHashMap<String, TServer>();

    @Override
    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    @Override
    protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
        log.info("impl => " + impl.getClass());
        log.info("type => " + type.getName());
        log.info("url => " + url);

        // 保证export的幂等性
        String addr = getAddr(url);
        TServer finalThriftServer;
        TServer thriftServer = serverMap.get(addr);
        if (thriftServer == null) {
            thriftServer = new TThreadedSelectorServer(buildArgs(impl, type, url));
            serverMap.put(addr, thriftServer);
            finalThriftServer = thriftServer;
            new Thread(() -> {
                log.info("Start Thrift Server");
                finalThriftServer.serve();
                log.info("Thrift server started.");
            }).start();
        } else {
            finalThriftServer = thriftServer;
        }

        return () -> {
            try {
                log.info("Close Thrift Server");
                finalThriftServer.stop();
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        };
    }

    private <T> TThreadedSelectorServer.Args buildArgs(T impl, Class<T> type, URL url) {
        TProcessor tprocessor;
        TThreadedSelectorServer.Args tArgs = null;
        String typeName = type.getName();
        TNonblockingServerSocket transport;
        try {
            if (typeName.endsWith(IFACE)) {
                String processorClsName = typeName.substring(0, typeName.indexOf(IFACE)) + PROCESSOR;

                Class<?> clazz = Class.forName(processorClsName);
                Constructor constructor = clazz.getConstructor(type);
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
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RpcException("Fail to create thrift server(" + url + ") : " + e.getMessage(), e);
        }

        if (tArgs == null) {
            String msg = "Fail to create thrift server(" + url + ") due to null args";
            log.error(msg);
            throw new RpcException(msg);
        }

        return tArgs;
    }

    @Override
    protected <T> T doRefer(Class<T> type, URL url) throws RpcException {

        log.info("type => " + type.getName());
        log.info("url => " + url);

        try {
            T thriftClient = null;
            String typeName = type.getName();
            if (typeName.endsWith(IFACE)) {
                String clientClsName = typeName.substring(0, typeName.indexOf(IFACE)) + CLIENT;
                Class<?> clazz = Class.forName(clientClsName);
                Constructor constructor = clazz.getConstructor(TProtocol.class);
                thriftClient = createClientProxy(url, constructor, type);
            }
            return thriftClient;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RpcException("Fail to create remoting client for service(" + url + "): " + e.getMessage(), e);
        }
    }

    private <T> T createClientProxy(URL url, Constructor constructor, Class<T> type) {
        Object proxy;
        try {
            proxy = new ProxyFactory(type, (MethodInterceptor) invocation -> {
                ThriftServerInfo serverInfo = ThriftServerInfo.of(url.getAddress());
                TProtocolProvider tProtocolProvider = TProtocolProvider.getInstance();
                TProtocol protocol = null;
                try {
                    protocol = tProtocolProvider.getConnection(serverInfo);
                    T thriftClient = (T) constructor.newInstance(protocol);
                    return invocation.getMethod().invoke(thriftClient, invocation.getArguments());
                } finally {
                    tProtocolProvider.returnConnection(serverInfo, protocol);
                }
            }).getProxy(ClassUtils.getDefaultClassLoader());
            log.info("thrift client opened for service(" + url + ")");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RpcException("Fail to create remoting client:" + e.getMessage(), e);
        }
        return (T) proxy;
    }
}
