package com.soyo.blog.repository

import com.soyo.blog.model.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findByAuthor(author: String): List<Article>

    fun findByTitle(title: String): Article?

    override fun findAll(): List<Article>

}