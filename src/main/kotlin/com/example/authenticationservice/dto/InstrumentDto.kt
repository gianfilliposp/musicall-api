package com.example.authenticationservice.dto

data class InstrumentDto (
    val id: Long,
    val name: String
) {
    constructor(): this(
        id = 0,
        name = ""
    )
}