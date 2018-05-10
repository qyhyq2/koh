package com.koh.thrift.util;

import com.koh.thrift.support.ThriftConstant;
import com.koh.thrift.support.thrift.ThriftServerConfig;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * Created by qianyuhang on 2018/5/10.
 */
public class ThriftServerConfigLoader {
    public static ThriftServerConfig loadThriftServerConfig(ConfigurableEnvironment environment) {
        ThriftServerConfig properties = new ThriftServerConfig();
        RelaxedDataBinder binder = new RelaxedDataBinder(properties, ThriftConstant.CONFIG_PREFIX);
        binder.bind(new PropertySourcesPropertyValues(environment.getPropertySources()));
        return properties;
    }
}
