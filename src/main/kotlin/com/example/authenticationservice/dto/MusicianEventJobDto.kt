package com.example.authenticationservice.dto


data class MusicianEventJobDto(
    val id: Long,
    val name: String,
    val cep: String,
    val imageUrl: String,
    var distance: Int = 0
) {
    constructor(id: Long, name: String, cep: String, imageUrl: String): this (
        id = id,
        name = name,
        cep = cep,
        imageUrl = imageUrl,
        distance = 0
    )
}
