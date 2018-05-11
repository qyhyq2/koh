package com.koh.thrift.client;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class AutoClearKeyedPool<K, T> extends GenericKeyedObjectPool<K, T> {

    public AutoClearKeyedPool(KeyedPooledObjectFactory<K, T> factory) {
        super(factory);
    }

    public AutoClearKeyedPool(KeyedPooledObjectFactory<K, T> factory, GenericKeyedObjectPoolConfig config) {
        super(factory, config);
    }

    @Override
    public void returnObject(K k, T obj) {
        super.returnObject(k, obj);
        if (getNumIdle() >= getNumActive()) {
            clear();
        }
    }

}