package com.koh.thrift.second;

import org.apache.thrift.TException;

/**
 * Created by qianyuhang on 2018/5/2.
 */
//@Service
public class SecondServiceImpl implements SecondService.Iface {

    @Override
    public short add(short a, short b) throws TException {
        return (short) (a + b);
    }
}
