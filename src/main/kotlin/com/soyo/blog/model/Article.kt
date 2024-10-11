package com.soyo.blog.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
// 将类声明为一个JPA实体类，代表数据库中的一个表
@Table(name = "articles")
// 指定表名
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 指定主键，并指定主键生成策略为自增
    val id: Long = 0,
    var title: String = "",
    var content: String = "",
    var author: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
)