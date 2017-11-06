package com.minol.energymonitor.config;

import com.minol.energymonitor.filter.CustomAuthenticationProvider;
import com.minol.energymonitor.filter.JWTAuthenticationFilter;
import com.minol.energymonitor.filter.JWTLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        关闭csrf验证
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()//跨域
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest().authenticated().and();
                // 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
//                .addFilterBefore(new JWTLoginFilter("/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//                // 添加一个过滤器验证其他请求的Token是否合法
//               .addFilterBefore(new JWTAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }
}
