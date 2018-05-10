package com.koh.util;

import com.koh.config.thrift.ThriftServerConfig;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by qianyuhang on 2018/5/10.
 */
public class ThriftServerConfigLoader {
    public static ThriftServerConfig loadThriftServerConfig(ConfigurableEnvironment environment) {
        ThriftServerConfig properties = new ThriftServerConfig();
        RelaxedDataBinder binder = new RelaxedDataBinder(properties, "thriftServer");
        binder.bind(new PropertySourcesPropertyValues(environment.getPropertySources()));
        return properties;
    }
}
