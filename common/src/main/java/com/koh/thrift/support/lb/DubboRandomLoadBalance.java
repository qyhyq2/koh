package com.koh.thrift.support.lb;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;

import java.util.List;
import java.util.stream.Collectors;

public class DubboRandomLoadBalance extends RandomLoadBalance {
    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        List<Invoker<T>> dubboInvokers = invokers.stream()
                .filter(invoker -> invoker.getUrl().getProtocol().equals(DubboProtocol.NAME))
                .collect(Collectors.toList());
        return super.doSelect(dubboInvokers, url, invocation);
    }
}
