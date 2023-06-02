package com.example.authenticationservice.dto

import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.EventJob
import com.example.authenticationservice.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Time
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.NotBlank

data class EventDto(
        val id: Long,
        val name: String,
        var cep: String,
        val number: Int,
        val eventDate: LocalDate,
        val startHour: Time,
        val durationHours: Int,
        val imageUrl: String,
        val eventJobs: List<EventJobDto>
) {
        var distance: Int = Int.MAX_VALUE

    constructor(event: Event) : this(
            id = event.id,
            name = event.name,
            cep = event.cep,
            number = event.number,
            eventDate = event.eventDate,
            startHour = event.startHour,
            durationHours = event.durationHours,
            imageUrl = event.imageUrl,
            eventJobs = event.eventJob.map { EventJobDto(it) }
    )
}
