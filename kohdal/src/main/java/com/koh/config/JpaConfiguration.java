package com.koh.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.koh.mapper",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)})
@EntityScan("com.koh.entity")
public class JpaConfiguration {
}
