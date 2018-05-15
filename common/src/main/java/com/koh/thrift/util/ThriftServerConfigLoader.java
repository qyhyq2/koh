package com.koh.thrift.util;

import com.koh.thrift.support.ThriftConstant;
import com.koh.thrift.support.thrift.ThriftServerConfig;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * Created by qianyuhang on 2018/5/10.
 */
public class ThriftServerConfigLoader {
    public static ThriftServerConfig loadThriftServerConfig(ConfigurableEnvironment environment) {
        ThriftServerConfig properties = Binder.get(environment)
                .bind(ThriftConstant.SERVER_CONFIG_PREFIX, ThriftServerConfig.class)
                .orElse(null);
        return properties;
    }
}
