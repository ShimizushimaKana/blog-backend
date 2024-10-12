package com.soyo.blog.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true) // 指定 username 为唯一键
    var username: String = "",
    var password: String = "",
    var email: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
)