package com.soyo.blog.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis()
)