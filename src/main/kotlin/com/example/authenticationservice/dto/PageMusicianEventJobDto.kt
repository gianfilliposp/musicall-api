package com.example.authenticationservice.dto

data class PageMusicianEventJobDto (
    val content: List<MusicianEventJobDto>,
    val pageNo: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)

