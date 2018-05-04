package com.koh.thrift;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * Created by qianyuhang on 2018/5/2.
 */
@Service
public class TestServiceImpl implements TestService.Iface {

    @Override
    public boolean exists(String path) throws TException {
        System.out.println("received path:" + path);
        return false;
    }

    @Override
    public String echo(String path) throws TException {
        System.out.println("received path:" + path);
        if ("1".equals(path)) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    @Override
    public Data getById(byte id) throws TException {
        System.out.println("received id:" + id);
        return new Data(id, "No." + id, new SubData(id, "NO." + id));
    }
}
