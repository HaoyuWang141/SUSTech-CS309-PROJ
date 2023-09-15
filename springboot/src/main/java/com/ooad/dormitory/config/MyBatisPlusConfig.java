package com.ooad.dormitory.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

  @Bean
  public MybatisPlusInterceptor paginationInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(
        DbType.POSTGRE_SQL);
    interceptor.addInnerInterceptor(paginationInnerInterceptor);
    return interceptor;
  }
}
