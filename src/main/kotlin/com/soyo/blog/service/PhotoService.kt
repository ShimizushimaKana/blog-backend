package com.soyo.blog.service

import com.aliyun.oss.OSS
import com.aliyun.oss.model.PutObjectRequest
import com.soyo.blog.config.OSSConfig
import com.soyo.blog.model.Photo
import com.soyo.blog.repository.PhotoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

@Service
class PhotoService(
    private val ossClient: OSS,
    private val ossConfig: OSSConfig,
    private val photoRepository: PhotoRepository
) {

    fun uploadPhotoToOSS(file: MultipartFile): String {
        // 获取文件名并生成唯一标识符
        val originalFilename = file.originalFilename ?: throw IllegalArgumentException("Invalid file")
        val fileKey = "photos/${UUID.randomUUID()}_${originalFilename}" // 保持文件名唯一，避免冲突

        // 上传文件
        val inputStream: InputStream = file.inputStream
        val putObjectRequest = PutObjectRequest(ossConfig.bucketName, fileKey, inputStream)
        ossClient.putObject(putObjectRequest)

        // 返回文件的访问 URL，从配置中获取 endpoint
        val fileUrl = "https://${ossConfig.bucketName}.${ossConfig.endpoint}/$fileKey"
        return fileUrl
    }

    fun uploadPhoto(file: MultipartFile, title: String, description: String?): Photo {
        val url = uploadPhotoToOSS(file)
        val currentUser = getCurrentUsername()
        val photo = Photo(
            title = title,
            description = description,
            url = url,
            uploadedBy = currentUser
        )
        return photoRepository.save(photo)
    }

    private fun getCurrentUsername(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.principal is UserDetails) {
            (authentication.principal as UserDetails).username
        } else {
            "anonymous"
        }
    }

    private fun deleteFileFromObjectStorage(url: String) {
        val fileKey = url.substringAfter("${ossConfig.bucketName}.${ossConfig.endpoint}/")
        ossClient.deleteObject(ossConfig.bucketName, fileKey)
    }

    fun getAllPhotos(page: Int, size: Int): List<Photo> {
        val pageable = PageRequest.of(page, size)
        return photoRepository.findAll(pageable).content
    }

    fun getPhotoById(id: Long): Photo? {
        return photoRepository.findById(id).orElse(null)
    }

    fun deletePhoto(id: Long) {
        val photo = getPhotoById(id)
        if (photo != null) {
            deleteFileFromObjectStorage(photo.url)
            photoRepository.deleteById(id)
        }
    }
}
