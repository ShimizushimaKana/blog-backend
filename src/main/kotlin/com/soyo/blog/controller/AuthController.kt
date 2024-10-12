package com.soyo.blog.controller

import com.soyo.blog.http.request.LoginRequest
import com.soyo.blog.model.User
import com.soyo.blog.service.UserService
import com.soyo.blog.util.JwtTokenUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<String> {
        if (userService.findByUsername(user.username) != null) {
            return ResponseEntity.badRequest().body("User already exists")
        }
        userService.registerUser(user)
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginReq: LoginRequest): ResponseEntity<Any> {
        try {
            // 使用 AuthenticationManager 验证用户身份
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginReq.username, loginReq.password)
            )

            // 设置认证信息到 Spring Security 上下文
            SecurityContextHolder.getContext().authentication = authentication

            // 获取用户信息并生成 JWT
            val user = userService.findByUsername(loginReq.username)
            return if (user != null) {
                val token = jwtTokenUtil.generateToken(loginReq.username)
                ResponseEntity.ok(mapOf("token" to token))
            } else {
                ResponseEntity.badRequest().body("Invalid credentials")
            }
        } catch (e: BadCredentialsException) {
            return ResponseEntity.badRequest().body("Invalid username or password")
        }
    }
}

