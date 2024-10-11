package com.soyo.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("com.soyo.blog.model")
class BlogApplication

fun main(args: Array<String>) {
	runApplication<BlogApplication>(*args)
}
