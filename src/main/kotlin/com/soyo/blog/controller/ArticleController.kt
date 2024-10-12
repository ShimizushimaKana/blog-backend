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
    fun getAllArticles(): MutableList<Article> = articleService.getAllArticles()

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): ResponseEntity<Article> {
        return articleService.getArticleById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.noContent().build()
    }

    @PostMapping
    fun createArticle(@RequestBody article: Article) = articleService.createArticle(article)

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: Long): ResponseEntity<Unit> {
        return articleService.getArticleById(id)?.let {
            articleService.deleteArticle(id)
            ResponseEntity.noContent().build()
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: Long, @RequestBody article: Article): ResponseEntity<Article> {
        return articleService.getArticleById(id)?.let {
            articleService.updateArticle(id, article)
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/search/author")
    fun findArticleByAuthor(@RequestParam author: String): ResponseEntity<List<Article>> {
        val articles = articleService.findArticlesByAuthor(author)
        return if (articles.isNotEmpty()) {
            ResponseEntity.ok(articles)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/search/title")
    fun findArticleByTitle(@RequestParam title: String): ResponseEntity<List<Article>> {
        val articles = articleService.findArticleByTitle(title)
        return if (articles.isNotEmpty()) {
            ResponseEntity.ok(articles)
        } else {
            ResponseEntity.noContent().build()
        }
    }

}