package com.koh.config.thrift;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.koh.util.ThriftServerConfigLoader;
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
                                .addPropertyValue("name", ThriftServerConfig.PROTOCOL_THRIFT)
                                .addPropertyValue("default", false);
                if (item.getPort() > 0) {
                    builder.addPropertyValue("port", item.getPort());
                }
                if (item.getThreads() > 0) {
                    builder.addPropertyValue("threads", item.getThreads());
                }
                if (!StringUtils.isEmpty(item.getDispatcher())) {
                    builder.addPropertyValue("dispatcher", item.getDispatcher());
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
