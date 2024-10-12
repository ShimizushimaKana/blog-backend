package com.soyo.blog.middleware

import com.soyo.blog.service.UserService
import com.soyo.blog.util.JwtTokenUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        // 从请求头中获取 token
        val authHeader = request.getHeader("Authorization")
        // 如果 token 存在，解析 token 并设置认证信息到 Spring Security 上下文
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取 token, 去掉 "Bearer " 前缀
            val jwt = authHeader.substring(7)
            val username = jwtTokenUtil.extractUsername(jwt)
            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userService.loadUserByUsername(username)
                if (jwtTokenUtil.validateToken(jwt, userDetails.username)) {
                    val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}
