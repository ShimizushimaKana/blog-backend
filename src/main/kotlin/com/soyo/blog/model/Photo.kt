package com.soyo.blog.model

import java.time.LocalDateTime
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "photos")
data class Photo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    var title: String,
    @Column(length = 1000)
    var description: String? = null,
    @Column(nullable = false)
    var url: String,
    @Column(name = "uploaded_by")
    var uploadedBy: String? = null,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
