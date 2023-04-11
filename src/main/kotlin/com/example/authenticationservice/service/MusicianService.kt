package com.example.authenticationservice.service

import com.example.authenticationservice.dao.EventRepository
import com.example.authenticationservice.dao.MusicianRepository
import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.dto.EventDto
import com.example.authenticationservice.mapper.EventMapper
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.parameters.CreateEventRequest
import com.example.authenticationservice.parameters.RegisterMusicianRequest
import com.example.authenticationservice.parameters.RegisterUserRequest
import com.example.authenticationservice.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@Service
class MusicianService ( @Autowired private val userRepository: UserRepository,
                        @Autowired private val jwtTokenProvider: JwtTokenProvider,
                        @Autowired private val musicianRepository: MusicianRepository
                        ) {
    fun registerMusician(registerMusicianRequest: RegisterMusicianRequest, req : HttpServletRequest){
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        if(!userRepository.existsByIdAndUserIsCompleted(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User NotFound")
        }
        musicianRepository.save(Musician(registerMusicianRequest, id))
    }
}
