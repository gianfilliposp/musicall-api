package com.example.authenticationservice.dto

import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.EventJob
import com.example.authenticationservice.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.NotBlank

data class EventDto(
        val id: Long,
        val creatorEventId: UserEventDto,
        val name: String,
        val cep: String,
        val eventDate: LocalDate,
        val durationHours: Int,
        val eventJobs: List<EventJobDto>
) {
    constructor(event: Event) : this(
            id = event.id,
            creatorEventId = UserEventDto(event.user),
            name = event.name,
            cep = event.cep,
            eventDate = event.eventDate,
            durationHours = event.durationHours,
            eventJobs = event.eventJob.map { EventJobDto(it) }
    )
}
