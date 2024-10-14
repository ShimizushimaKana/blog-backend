package com.soyo.blog.controller

import com.soyo.blog.service.PhotoService
import com.soyo.blog.model.Photo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/photos")
class PhotoController(private val photoService: PhotoService) {

    @PostMapping("/upload")
    fun uploadPhoto(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String?
    ): ResponseEntity<Photo> {
        val uploadedPhoto = photoService.uploadPhoto(file, title, description)
        return ResponseEntity.ok(uploadedPhoto)
    }

    @GetMapping
    fun getAllPhotos(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<List<Photo>> {
        val photos = photoService.getAllPhotos(page, size)
        return ResponseEntity.ok(photos)
    }

    @GetMapping("/{id}")
    fun getPhotoById(@PathVariable id: Long): ResponseEntity<Photo> {
        return photoService.getPhotoById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deletePhoto(@PathVariable id: Long): ResponseEntity<Unit> {
        return photoService.getPhotoById(id)?.let {
            photoService.deletePhoto(id)
            ResponseEntity.noContent().build()
        } ?: ResponseEntity.notFound().build()
    }

}