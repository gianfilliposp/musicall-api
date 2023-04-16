package com.example.authenticationservice.service

import com.example.authenticationservice.dao.EventJobRepository
import com.example.authenticationservice.dao.EventRepository
import com.example.authenticationservice.dao.InstrumentRepository
import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.dto.CreateEventDto
import com.example.authenticationservice.dto.EventJobDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.EventJob
import com.example.authenticationservice.model.Instrument
import com.example.authenticationservice.parameters.CreateEventJobRequest
import com.example.authenticationservice.parameters.CreateEventRequest
import com.example.authenticationservice.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@Service
class OrganizerService (
        @Autowired private val eventRepository: EventRepository,
        @Autowired private val jwtTokenProvider: JwtTokenProvider,
        @Autowired private val userRepository: UserRepository,
        @Autowired private val instrumentRepository: InstrumentRepository,
        @Autowired private val eventJobRepository : EventJobRepository
) {
    fun createEvent(createEventRequest: CreateEventRequest, req : HttpServletRequest) : CreateEventDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        if (eventRepository.existsByEventDateAndFinalized(createEventRequest.eventDate, false)) throw ResponseStatusException(HttpStatus.CONFLICT, "You have already an event at this date")

        val event = Event(createEventRequest, user)

        eventRepository.save(event)

        return CreateEventDto(event)
    }

    fun createEventJob(createEventJobRequest: CreateEventJobRequest, req: HttpServletRequest): List<EventJobDto> {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        val event = eventRepository.findByIdAndUserAndFinalized(createEventJobRequest.fkEvent!!, user, false) ?: throw ResponseStatusException(HttpStatus.CONFLICT, "You cant create jobs for this event")

        val instruments = instrumentRepository.findAll()
        val instrumentMap : HashMap<Long, Instrument> = HashMap()
        instruments.forEach { instrumentMap[it.id] = it }

        val eventJobs = createEventJobRequest.fkInstrument!!.map {
            if (instrumentMap[it] != null) EventJob(event, instrumentMap[it]!!)
            else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Instrument not found")
        }

       eventJobRepository.saveAll(eventJobs)

        return eventJobs.map{ EventJobDto(it) }
    }
}
