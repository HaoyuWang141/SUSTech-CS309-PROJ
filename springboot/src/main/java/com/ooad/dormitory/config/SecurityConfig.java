package com.ooad.dormitory.config;


//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends SecurityConfigurerAdapter<HttpSecurity, Void> {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 定义用户信息服务，可以是从数据库中读取用户信息
//        // 返回的 UserDetails 对象包含用户名、密码、权限等信息
//        // 这里示例使用 InMemoryUserDetailsManager
//        return new InMemoryUserDetailsManager(
//                User.withUsername("user")
//                        .password("{noop}password")
//                        .roles("USER")
//                        .build());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // 密码编码器，这里示例使用 NoOpPasswordEncoder
//        // 实际生产中应该使用安全的密码编码器
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/public/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//
//        // 在这里添加自定义的过滤器，如果需要的话
//        // http.addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//package com.ooad.dormitory.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends SecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService; // Inject your UserDetailsService implementation
//
//    @Bean
//    public CustomAuthenticationFilter customAuthenticationFilter() {
//        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/student/login").permitAll() // Allow login endpoint without authentication
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//}
//
