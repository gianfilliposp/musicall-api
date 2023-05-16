package com.example.authenticationservice.service

import com.example.authenticationservice.dao.*
import com.example.authenticationservice.dto.*
import com.example.authenticationservice.model.*
import com.example.authenticationservice.model.JobRequest
import com.example.authenticationservice.parameters.*
import com.example.authenticationservice.security.JwtTokenProvider
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Future
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Service
class OrganizerService (
    @Autowired private val eventRepository: EventRepository,
    @Autowired private val jwtTokenProvider: JwtTokenProvider,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val instrumentRepository: InstrumentRepository,
    @Autowired private val jobRequestRepository: JobRequestRepository,
    @Autowired private val notificationRepository: NotificationRepository,
    @Autowired private val eventJobRepository : EventJobRepository
) {
    fun createEvent(createEventRequest: CreateEventRequest, req : HttpServletRequest) : CreateEventDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        if (eventRepository.existsByEventDateAndFinalized(createEventRequest.eventDate!!, false)) throw ResponseStatusException(HttpStatus.CONFLICT, "You have already an event at this date")

        val event = Event(createEventRequest, user)

        eventRepository.save(event)

        return CreateEventDto(event)
    }

    fun createEventJob(createEventJobRequest: CreateEventJobRequest, req: HttpServletRequest): List<EventJobDto> {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val eventId = eventRepository.findIdByIdAndUserIdAndFinalizedFalse(createEventJobRequest.fkEvent!!, id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "You cant create jobs for this event")
        val instrumentsEventJobIds = createEventJobRequest.jobs!!.map { it.instrumentId }

        val instruments = instrumentRepository.findInstrumentDtoByIdIn(instrumentsEventJobIds)
        val instrumentsHash = hashMapOf<Long, InstrumentDto?>()
        instruments.forEach { instrumentsHash[it.id] = it }


        val eventJobs = mutableListOf<EventJob>()
        createEventJobRequest.jobs!!.forEach {
            if (instrumentsHash.containsKey(it.instrumentId)) for(i in 1 .. it.quantity) { eventJobs.add(EventJob(Event(eventId), Instrument(id = it.instrumentId, name = instrumentsHash[it.instrumentId]!!.name), it.payment)) }
            else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Instrument not found")
        }

       eventJobRepository.saveAll(eventJobs)
        return eventJobs.map{ EventJobDto(it) }
    }

    @Transactional
    fun deleteEvent(req: HttpServletRequest, deleteEventRequest: DeleteEventRequest) {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()

        if (!eventRepository.existsByIdAndUserIdAndFinalizedFalse(deleteEventRequest.id!!, id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this event")

        notificationRepository.deleteByJobRequestEventJobEventId(deleteEventRequest.id!!)
        jobRequestRepository.deleteByEventJobEventId(deleteEventRequest.id!!)
        eventJobRepository.deleteByEventId(deleteEventRequest.id!!)
        eventRepository.deleteById(deleteEventRequest.id!!)
}

    fun updateEvent(updateEventRequest: UpdateEventRequest, req: HttpServletRequest): CreateEventDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        val event = eventRepository.findByIdAndUserAndFinalized(updateEventRequest.id!!, user, false) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this event")

        var hasChanges = false

        if (updateEventRequest.name != null) {
            event.name = if (updateEventRequest.name == event.name) throw ResponseStatusException(HttpStatus.CONFLICT, "The name is the same") else updateEventRequest.name
            hasChanges = true
        }

        if (updateEventRequest.aboutEvent != null) {
            event.aboutEvent = if (updateEventRequest.aboutEvent == event.aboutEvent) throw ResponseStatusException(HttpStatus.CONFLICT, "The aboutEvent is the same") else updateEventRequest.aboutEvent
            hasChanges = true
        }

        if (updateEventRequest.cep != null) {
            event.cep = if (updateEventRequest.cep == event.cep) throw ResponseStatusException(HttpStatus.CONFLICT, "The cep is the same") else updateEventRequest.cep
            hasChanges = true
        }

        if (updateEventRequest.number != null) {
            event.number = if (updateEventRequest.number == event.number) throw ResponseStatusException(HttpStatus.CONFLICT, "The number is the same") else updateEventRequest.number
            hasChanges = true
        }

        if (updateEventRequest.complement != null) {
            event.complement = if (updateEventRequest.complement == event.complement) throw ResponseStatusException(HttpStatus.CONFLICT, "The local is the same") else updateEventRequest.complement
            hasChanges = true
        }

        if (updateEventRequest.eventDate != null){
            event.eventDate = if (updateEventRequest.eventDate == event.eventDate) throw ResponseStatusException(HttpStatus.CONFLICT, "The event date is the same") else updateEventRequest.eventDate
            hasChanges = true
        }

        if (updateEventRequest.startHour != null){
            event.startHour = if (updateEventRequest.startHour == event.startHour) throw ResponseStatusException(HttpStatus.CONFLICT, "The start hour is the same") else updateEventRequest.startHour
            hasChanges = true
        }

        if (updateEventRequest.durationHours != null) {
            event.durationHours = if (updateEventRequest.durationHours == event.durationHours) throw ResponseStatusException(HttpStatus.CONFLICT, "The duration in hours is the same") else updateEventRequest.durationHours
            hasChanges = true
        }

        if (!hasChanges) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have any change")

        eventRepository.save(event)

        return CreateEventDto(event)
    }

    @Transactional
    fun deleteEventJob(req: HttpServletRequest, eventJobId: Long?) {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()

        if (!eventJobRepository.existsByIdAndEventUserIdAndEventFinalizedFalse(eventJobId!!, id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this event job")

        notificationRepository.deleteByJobRequestId(eventJobId!!)
        jobRequestRepository.deleteByEventJobId(eventJobId)
        eventJobRepository.deleteById(eventJobId)
    }

    @Transactional
    fun approveJobRequest(id: Long, jobRequestId: Long?) {
        val userId = jobRequestRepository.findUserIdByIdAndUserIdAndMusicianConfirmedTrue(jobRequestId!!, id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "You cannot approve this job request")
        jobRequestRepository.updateOrganizerConfirmedTrueById(jobRequestId)

        val user = User()
        user.id = userId

        val jobRequest = JobRequest()
        jobRequest.id = jobRequestId!!

        notificationRepository.save(
            Notification(
                user = user,
                jobRequest = jobRequest,
                notificationType = NotificationTypeDto.CONFIRM
            )
        )
    }
}