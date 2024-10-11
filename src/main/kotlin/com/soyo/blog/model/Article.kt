package com.soyo.blog.model

import java.time.LocalDateTime


data class Article(
    val id: Long = 0,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)