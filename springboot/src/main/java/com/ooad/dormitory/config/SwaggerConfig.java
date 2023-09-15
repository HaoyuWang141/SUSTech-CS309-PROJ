package com.ooad.dormitory.config;//package sustech.project.javaproject.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//// @Controller
//// @EnableWebMvc
//@Configuration
//@EnableSwagger2
//// public class Swagger2Config implements WebMvcConfigurer {
//public class SwaggerConfig {
//
//  // @Override
//  // public void addResourceHandlers(ResourceHandlerRegistry registry) {
//  //
//  //   registry.addResourceHandler("/**").addResourceLocations(
//  //       "classpath:/static/");
//  //   registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//  //       "classpath:/META-INF/resources/");
//  //   registry.addResourceHandler("/webjars/**").addResourceLocations(
//  //       "classpath:/META-INF/resources/webjars/");
//  //   WebMvcConfigurer.super.addResourceHandlers(registry);
//  // }
//
//  @Bean
//  public Docket createRestApi() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .apiInfo(apiInfo())
//        .select()
//        .apis(RequestHandlerSelectors.basePackage("com")) // com包下所有API都交给Swagger2管理
//        .paths(PathSelectors.any()).build();
//  }
//
//  //  API文档页面显示信息
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//        .title("演示项目")
//        .description("学习")
//        .build();
//  }
//}
