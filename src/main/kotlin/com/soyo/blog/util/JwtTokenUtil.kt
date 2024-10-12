package com.soyo.blog.util

import com.soyo.blog.config.JwtConfig
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenUtil(private val jwtConfig: JwtConfig) {

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username) // 将用户名设置为 token 的主题
            .setIssuedAt(Date()) // 设置 token 的签发时间
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expirationTime)) // 设置 token 的过期时间
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secretKey) // 设置 token 的签名算法和密钥
            .compact()
    }

    // 从 token 中解析出用户名
    fun extractUsername(token: String): String {
        return Jwts.parser()
            .setSigningKey(jwtConfig.secretKey)
            .parseClaimsJws(token)
            .body
            .subject
    }

    // 验证 JWT 是否有效
    fun validateToken(token: String, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return extractedUsername == username && !isTokenExpired(token)
    }

    // 验证 token 是否过期
    private fun isTokenExpired(token: String): Boolean {
        return Jwts.parser()
            .setSigningKey(jwtConfig.secretKey)
            .parseClaimsJws(token)
            .body
            .expiration
            .before(Date())
    }

}