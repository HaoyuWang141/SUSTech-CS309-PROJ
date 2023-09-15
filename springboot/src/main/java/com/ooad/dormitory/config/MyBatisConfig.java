package com.ooad.dormitory.config;// package sustech.project.javaproject.config;
//
// import org.apache.ibatis.session.SqlSessionFactory;
// import org.mybatis.spring.SqlSessionFactoryBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
// import javax.sql.DataSource;
//
// @Configuration
// public class MyBatisConfig {
//
//   @Bean
//   public static DataSource dataSource() {
//     // 配置数据源
//     DriverManagerDataSource dataSource = new DriverManagerDataSource();
//     dataSource.setDriverClassName("org.postgresql.Driver");
//     dataSource.setUrl("jdbc:postgresql://114.132.51.227:5432/java2_project");
//     dataSource.setUsername("admin_java2_project");
//     dataSource.setPassword("@4Em~hD9EoPkEVEL3tgWY@");
//     return dataSource;
//   }
//
//   @Bean
//   public static SqlSessionFactory getSqlSessionFactory() throws Exception {
//     // 配置 MyBatis 的 SqlSessionFactory
//     SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//     sessionFactory.setDataSource(dataSource());
//     // 设置映射器接口的包路径
//     sessionFactory.setTypeAliasesPackage("sustech.project.javaproject.mapper");
//     return sessionFactory.getObject();
//   }
// }
//
