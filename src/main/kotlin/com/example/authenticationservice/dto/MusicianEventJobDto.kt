package com.example.authenticationservice.dto


data class MusicianEventJobDto(
    val id: Long,
    val name: String,
    val cep: String,
    var distance: Int
) {
}
