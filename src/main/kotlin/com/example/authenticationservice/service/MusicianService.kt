package com.example.authenticationservice.service

import com.example.authenticationservice.dao.InstrumentRepository
import com.example.authenticationservice.dao.MusicianInstrumentRepository
import com.example.authenticationservice.dao.MusicianRepository
import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.dto.MusicianDto
import com.example.authenticationservice.dto.InstrumentsDto
import com.example.authenticationservice.mapper.MusicianMapper
import com.example.authenticationservice.model.EventJob
import com.example.authenticationservice.model.Instrument
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.MusicianInstrument
import com.example.authenticationservice.parameters.RegisterInstrumentRequest
import com.example.authenticationservice.parameters.RegisterMusicianRequest
import com.example.authenticationservice.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@Service
class MusicianService (
        @Autowired private val userRepository: UserRepository,
        @Autowired private val jwtTokenProvider: JwtTokenProvider,
        @Autowired private val musicianRepository: MusicianRepository,
        @Autowired private val musicianMapper : MusicianMapper,
        @Autowired private val musicianInstrumentRepository: MusicianInstrumentRepository,
        @Autowired private val instrumentRepository: InstrumentRepository
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
}
