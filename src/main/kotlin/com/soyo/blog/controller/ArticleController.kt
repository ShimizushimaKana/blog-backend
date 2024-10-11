package com.soyo.blog.controller

import com.soyo.blog.model.Article
import com.soyo.blog.service.ArticleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
// 将类声明为一个控制器
@RequestMapping("/api/articles")
// 指定路由前缀
class ArticleController(private val articleService: ArticleService) {
    @GetMapping
    // 指定路由
    fun getAllArticles() = articleService.getAllArticles()

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): ResponseEntity<Article> {
        return articleService.getArticleById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createArticle(@RequestBody article: Article) = articleService.createArticle(article)

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: Long): ResponseEntity<Unit> {
        return articleService.getArticleById(id)?.let {
            articleService.deleteArticle(id)
            ResponseEntity.noContent().build<Unit>()
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: Long, @RequestBody article: Article): ResponseEntity<Article> {
        return articleService.getArticleById(id)?.let {
            articleService.updateArticle(id, article)
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }
}