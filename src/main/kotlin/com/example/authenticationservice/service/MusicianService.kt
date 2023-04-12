package com.example.authenticationservice.service

import com.example.authenticationservice.dao.MusicianInstrumentRepository
import com.example.authenticationservice.dao.MusicianRepository
import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.dto.MusicianDto
import com.example.authenticationservice.dto.InstrumentsDto
import com.example.authenticationservice.mapper.MusicianMapper
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
        @Autowired private val musicianInstrumentRepository: MusicianInstrumentRepository
) {
    fun registerMusician(registerMusicianRequest: RegisterMusicianRequest, req : HttpServletRequest) : MusicianDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        if(!userRepository.existsById(id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "User NotFound")

        val musician = Musician(registerMusicianRequest, id)
        musicianRepository.save(musician)

        return musicianMapper.toDto(musician)
    }

    fun registerInstrument(registerInstrumentRequest: RegisterInstrumentRequest, req: HttpServletRequest): InstrumentsDto {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
     if (!musicianRepository.existsByfkUser(id)) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Instrument NotFound")


        val musicianInstruments:List<MusicianInstrument> = registerInstrumentRequest.fkInstrument.map { MusicianInstrument(fkInstrument = it, fkMusician = id) }
        musicianInstrumentRepository.saveAll(musicianInstruments)
        return InstrumentsDto(registerInstrumentRequest.fkInstrument)
    }
}
