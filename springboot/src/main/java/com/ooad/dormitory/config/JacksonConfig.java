package com.ooad.dormitory.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

    // 将驼峰命名的属性映射为下划线命名的属性
    builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    // 开启驼峰命名法转换为下划线命名法的功能
    builder.featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);

    return builder;
  }
}

