package com.koh.thrift.test;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.thrift.TException;

/**
 * Created by qianyuhang on 2018/5/2.
 */
@Service(protocol = {"testService"})
public class TestServiceImpl implements TestService.Iface {

    @Override
    public boolean exists(String path) throws TException {
        System.out.println("received path:" + path);
        return true;
    }

    @Override
    public String echo(String path) throws TException {
        System.out.println("received path:" + path);
        if ("1".equals(path)) {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    @Override
    public Data getById(int id) throws TException {
        System.out.println("received id:" + id);
        return new Data(id, "No." + id, new SubData(id, "NO." + id));
    }
}
