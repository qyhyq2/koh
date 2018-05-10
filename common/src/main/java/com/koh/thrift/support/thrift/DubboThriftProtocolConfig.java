package com.koh.thrift.support.thrift;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.koh.thrift.util.ThriftServerConfigLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import static com.koh.thrift.support.ThriftConstant.*;

/**
 * @author qianyuhang
 * @date 2018/5/9
 */
@Configuration
public class DubboThriftProtocolConfig implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ThriftServerConfig config = ThriftServerConfigLoader.loadThriftServerConfig((ConfigurableEnvironment) applicationContext.getEnvironment());

        if (config != null && config.getConfigs() != null) {
            config.getConfigs().forEach(item -> {
                BeanDefinitionBuilder builder =
                        BeanDefinitionBuilder.rootBeanDefinition(ProtocolConfig.class)
                                .addPropertyValue(PROTOCOL_KEY_NAME, PROTOCOL_THRIFT)
                                .addPropertyValue(PROTOCOL_KEY_DEFAULT, false);
                if (item.getPort() > 0) {
                    builder.addPropertyValue(PROTOCOL_KEY_PORT, item.getPort());
                }
                if (item.getThreads() > 0) {
                    builder.addPropertyValue(PROTOCOL_KEY_THREADS, item.getThreads());
                }
                if (!StringUtils.isEmpty(item.getName())) {
                    registry.registerBeanDefinition(item.getName(), builder.getBeanDefinition());
                }
            });
        }
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
