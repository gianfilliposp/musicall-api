package com.example.authenticationservice.service

import com.example.authenticationservice.dao.EventRepository
import com.example.authenticationservice.dto.EventDto
import com.example.authenticationservice.mapper.EventMapper
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.parameters.CreateEventRequest
import com.example.authenticationservice.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@Service
class EventService (
        @Autowired private val eventRepository: EventRepository,
        @Autowired private val eventMapper: EventMapper,
        @Autowired private val jwtTokenProvider: JwtTokenProvider
) {
    fun createEvent(createEventRequest: CreateEventRequest, req : HttpServletRequest) : EventDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val event = Event(createEventRequest, id)

        eventRepository.save(event)

        return eventMapper.toDto(event)
    }
}
