package com.example.authenticationservice.mapper

import com.example.authenticationservice.dto.EventDto
import com.example.authenticationservice.model.Event
import org.springframework.stereotype.Component

@Component
class EventMapper {
    fun toDto(event : Event) : EventDto {
        return EventDto(
                id = event.id,
                creatorEventId = event.creatorEvent,
                name = event.name,
                local = event.local,
                eventDate = event.eventDate,
                durationHours = event.durationHours,
                salary = event.salary
        )
    }
}