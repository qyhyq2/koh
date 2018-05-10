package com.koh.thrift.support.thrift;


import java.util.List;

/**
 * @author qianyuhang
 * @date 2018/5/9
 */
public class ThriftServerConfig {
    List<ThriftServerConfigItem> configs;

    public List<ThriftServerConfigItem> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ThriftServerConfigItem> configs) {
        this.configs = configs;
    }
}
