package com.soyo.blog.config

import com.soyo.blog.middleware.JwtAuthenticationEntryPoint
import com.soyo.blog.middleware.JwtAuthenticationFilter
import com.soyo.blog.service.UserService
import com.soyo.blog.util.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    @Bean
    fun passwordEncode(): PasswordEncoder = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable() // 禁用 CSRF，适合开发或测试阶段, CSRF 是一种 Web 攻击, 用于伪造用户请求
            .authorizeRequests() // 开始配置权限
            .antMatchers("/api/auth/register", "/api/auth/login").permitAll() // 允许匿名访问的路径
            .anyRequest().authenticated() // 其他路径需要身份认证
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) // 配置异常处理
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 禁用 session
        http.addFilterBefore(JwtAuthenticationFilter(jwtTokenUtil, userService), UsernamePasswordAuthenticationFilter::class.java) // 添加 JWT 过滤器
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // 配置用户认证服务和密码加密方式
        auth.userDetailsService(userService).passwordEncoder(passwordEncode())
    }
}