package com.koh.config.thrift;

import lombok.Data;

import java.util.List;

/**
 * @author qianyuhang
 * @date 2018/5/9
 */
@Data
public class ThriftServerConfig {
    public static final String PROTOCOL_THRIFT = "thrift";

    List<ThriftServerConfigItem> configs;
}
