package com.example.authenticationservice.service

import com.example.authenticationservice.parameters.UpdateMusicianRequest
import com.example.authenticationservice.utils.GoogleMapsUtils
import com.example.authenticationservice.dao.*
import com.example.authenticationservice.dto.EventDto
import com.example.authenticationservice.dto.MusicianDto
import com.example.authenticationservice.dto.InstrumentsDto
import com.example.authenticationservice.mapper.MusicianMapper
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.Instrument
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.MusicianInstrument
import com.example.authenticationservice.parameters.CreateJobRequestRequest
import com.example.authenticationservice.parameters.RegisterInstrumentRequest
import com.example.authenticationservice.parameters.RegisterMusicianRequest
import com.example.authenticationservice.security.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

@Service
class MusicianService (
        @Autowired private val userRepository: UserRepository,
        @Autowired private val jwtTokenProvider: JwtTokenProvider,
        @Autowired private val musicianRepository: MusicianRepository,
        @Autowired private val musicianMapper : MusicianMapper,
        @Autowired private val musicianInstrumentRepository: MusicianInstrumentRepository,
        @Autowired private val instrumentRepository: InstrumentRepository,
        @Autowired private val eventRepository: EventRepository,
        @Autowired private val googleMapsService: GoogleMapsUtils
) {
    fun registerMusician(registerMusicianRequest: RegisterMusicianRequest, req : HttpServletRequest) : MusicianDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        if(musicianRepository.existsByUser(user)) throw ResponseStatusException(HttpStatus.CONFLICT, "User already exists")

        val musician = Musician(registerMusicianRequest, user)

        musicianRepository.save(musician)

        return musicianMapper.toDto(musician)
    }

    fun registerInstrument(registerInstrumentRequest: RegisterInstrumentRequest, req: HttpServletRequest): List<InstrumentsDto> {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        val musician : Musician? = musicianRepository.getByUser(user) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Complete your register as musician")
        val instrumentsOfUser = musicianInstrumentRepository.findAll().map { it.instrument }
        if (musicianInstrumentRepository.existsByInstrumentIn(instrumentsOfUser)) throw ResponseStatusException(HttpStatus.CONFLICT, "Instrument already registered")

        val instruments = instrumentRepository.findAll()
        val instrumentMap : HashMap<Long, Instrument> = HashMap()
        instruments.forEach { instrumentMap[it.id] = it }

//        instrumentsOfUser.forEach { if (instrumentMap.containsKey(it.instrument.id)) throw ResponseStatusException(HttpStatus.CONFLICT, "The instrument is already registered") }

        val musicianInstruments = registerInstrumentRequest.fkInstrument!!.map {
            if (instrumentMap[it] != null) MusicianInstrument(musician = musician!!, instrument = instrumentMap[it]!!)
            else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Instrument not found")
        }

        musicianInstrumentRepository.saveAll(musicianInstruments)

        return musicianInstruments.map { InstrumentsDto(it.instrument.id, it.instrument.name) }
    }
    fun getEventsByLocation(req: HttpServletRequest): List<EventDto> {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val cep = musicianRepository.findCepByUserId(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Complete your register as a musician")
        val instrumentsHash = musicianRepository.findInstrumentIdsByUserId(id).toHashSet()
        if (instrumentsHash.isEmpty()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "You need to add instruments first")

        var events = eventRepository.findUnfinalizedEventsAfterOrEqual(LocalDate.now())

        val eventsRes = mutableListOf<Event>()
        event@ for (event in events) {
            for (job in event.eventJob) {
                if (instrumentsHash.contains(job.instrument.id)) {
                    eventsRes.add(event)
                    continue@event
                }
            }
        }

        if (eventsRes.size < 1) throw ResponseStatusException(HttpStatus.NO_CONTENT, "No event was found for you")

        var destinations: String = ""
        eventsRes.forEach { destinations+=it.cep + "|"}
        destinations = destinations.dropLast(1)

        val response = googleMapsService.getDistanceMatrix(cep, destinations)
        val mapper = ObjectMapper()
        val data = mapper.readValue(response, Map::class.java)
        val eventsDto = eventsRes.map { EventDto(it) }

        val rows = data["rows"] as List<*>
        for ((rowIndex, row) in rows.withIndex()) {
            if (row is Map<*, *>) {
                val elements = row["elements"] as List<*>
                for ((elementIndex, element) in elements.withIndex()) {
                    if (element is Map<*, *> && element["status"] as? String == "OK") {
                        val distance = (element["distance"] as Map<String, Any>)["value"] as Int
                        val address = (data["destination_addresses"] as List<String>)
                        eventsDto[elementIndex].cep =  address[elementIndex]
                        eventsDto[elementIndex].distance = distance
                    }
                }
            }
        }

        println(eventsDto)

        return eventsDto.sortedBy { it.distance }
    }

    fun updateMusician(updateMusicianRequest: UpdateMusicianRequest, req: HttpServletRequest) {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val musician = musicianRepository.getMusicianByUserId(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")

        if (updateMusicianRequest.cep.isNullOrBlank() and updateMusicianRequest.description.isNullOrBlank()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "The body is missing information")

        var hasChanges = false


        if (updateMusicianRequest.cep != null) {
            musician.cep = updateMusicianRequest.cep!!
            hasChanges = true
        }

        if (updateMusicianRequest.description != null) {
            musician.description = updateMusicianRequest.description!!
            hasChanges = true
        }

        if (!hasChanges) throw ResponseStatusException(HttpStatus.CONFLICT, "The musician info is the same")

        musicianRepository.save(musician)
    }

    fun createJobRequest(req: HttpServletRequest, createJobRequestRequest: CreateJobRequestRequest) {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        if (!musicianRepository.existsByUserId(id)) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Musician not registered, complete your register")

    }
}
