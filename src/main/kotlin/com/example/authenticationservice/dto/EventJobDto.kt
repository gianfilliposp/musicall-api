package com.example.authenticationservice.dto

import java.time.LocalDate

data class EventJobDto (
    val id: Long,
    val creatorEventId: Long,
    val name: String,
    val local: String,
    val eventDate: LocalDate,
    val durationHours: Int,
    val salary : Float
) {
}