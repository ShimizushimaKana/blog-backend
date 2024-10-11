package com.soyo.blog.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable() // 禁用 CSRF，适合开发或测试阶段
            .authorizeRequests()
            .antMatchers("/", "/api/articles", "/api/photos").permitAll() // 允许匿名访问的路径
            .anyRequest().authenticated() // 其他路径需要身份认证
            .and()
            .httpBasic() // 使用 Basic Auth 认证方式
    }
}