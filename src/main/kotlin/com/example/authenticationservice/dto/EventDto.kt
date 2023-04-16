package com.example.authenticationservice.dto

import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.NotBlank

data class EventDto(
        val id: Long,
        val creatorEventId: UserDto,
        val name: String,
        val local: String,
        val eventDate: LocalDate,
        val durationHours: Int
) {
    constructor(event: Event) : this(
            id = event.id,
            creatorEventId = UserDto(event.user),
            name = event.name,
            local = event.local,
            eventDate = event.eventDate,
            durationHours = event.durationHours
    )
}
