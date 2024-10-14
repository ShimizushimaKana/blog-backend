package com.soyo.blog.config

import com.aliyun.oss.OSS
import com.aliyun.oss.OSSClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OSSConfig{
    @Value("\${aliyun.oss.bucket-name}")
    lateinit var bucketName: String

    @Value("\${aliyun.oss.endpoint}")
    lateinit var endpoint: String

    @Value("\${aliyun.access-key}")
    lateinit var accessKey: String

    @Value("\${aliyun.secret-key}")
    lateinit var secretKey: String

    @Bean
    fun ossClient(): OSS {
        return OSSClientBuilder()
            .build(endpoint, accessKey, secretKey)
    }
}