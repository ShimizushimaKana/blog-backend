package com.soyo.blog.repository

import com.soyo.blog.model.Photo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhotoRepository: JpaRepository<Photo, Long> {

}