package com.soyo.blog.service

import com.soyo.blog.model.User
import com.soyo.blog.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository): UserDetailsService {
    private val passwordEncoder = BCryptPasswordEncoder() // 密码加密

    fun registerUser(user: User): User {
        user.password = passwordEncoder.encode(user.password) // 对密码进行加密
        return userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)?: throw UsernameNotFoundException("User not found: $username")
        return org.springframework.security.core.userdetails.User(user.username, user.password, emptyList())
    }

}