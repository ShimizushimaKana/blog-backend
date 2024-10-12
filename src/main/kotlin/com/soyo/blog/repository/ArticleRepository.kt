package com.soyo.blog.repository

import com.soyo.blog.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findByAuthor(author: String): List<Article> {
        return findAll().filter { it.author == author }
    }

    fun findByTitleContainingIgnoreCase(title: String): List<Article>

}