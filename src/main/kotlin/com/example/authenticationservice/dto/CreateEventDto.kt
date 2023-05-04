package com.example.authenticationservice.dto

import com.example.authenticationservice.model.Event
import java.sql.Time
import java.time.LocalDate

data class CreateEventDto (
        val id: Long,
        val name: String,
        val cep: String,
        val number: Int,
        val eventDate: LocalDate,
        val startHour: Time,
        val durationHours: Int
) {
    constructor(event: Event): this(
            id = event.id,
            name = event.name,
            cep = event.cep,
            number = event.number,
            eventDate = event.eventDate,
            startHour = event.startHour,
            durationHours = event.durationHours
    )
}