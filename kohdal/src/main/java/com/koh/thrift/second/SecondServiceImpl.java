package com.koh.thrift.second;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.thrift.TException;

/**
 * Created by qianyuhang on 2018/5/2.
 */
//@Service
public class SecondServiceImpl implements SecondService.Iface {

    @Override
    public short add(int a, int b) throws TException {
        return (short) (a + b);
    }
}
