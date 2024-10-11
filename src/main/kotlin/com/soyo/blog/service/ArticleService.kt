package com.soyo.blog.service

import com.soyo.blog.model.Article
import com.soyo.blog.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
// 将类声明为一个服务类
class ArticleService(private val articleRepository: ArticleRepository) {
    fun getAllArticles() = articleRepository.findAll()

    fun getArticleById(id: Long): Article? = articleRepository.findById(id).orElse(null)

    fun createArticle(article: Article) = articleRepository.save(article)

    fun deleteArticle(id: Long) = articleRepository.deleteById(id)

    fun updateArticle(id: Long, article: Article) {
        articleRepository.findById(id).orElse(null).let {
            it.title = article.title
            it.content = article.content
            it.author = article.author
            articleRepository.save(it)
        }
    }
}