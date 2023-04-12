package com.example.authenticationservice.controller

import com.example.authenticationservice.dto.MusicianDto
import com.example.authenticationservice.parameters.RegisterInstrumentRequest
import com.example.authenticationservice.exceptions.InvalidJwtAuthenticationException
import com.example.authenticationservice.parameters.RegisterMusicianRequest
import com.example.authenticationservice.service.MusicianService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.HashMap

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/msc")
class MusicianController (
    @Autowired private val musicianService: MusicianService
) {
    @PostMapping("/register")
    fun registerMusician(req : HttpServletRequest, @Valid @RequestBody registerMusicianRequest: RegisterMusicianRequest): ResponseEntity<MusicianDto> {
        val musicianDto = musicianService.registerMusician(registerMusicianRequest, req)
        /*emailSenderService.sendEmail(
            "${registerUserRequest.email}",
            "Email de confirmação",
            "http://localhost:8080/api/register/${registerUserRequest.email}/${token}"
        )*/

        return ResponseEntity.status(201).body(musicianDto)
    }

    @PostMapping("/instrument")
    fun registerInstrument(req: HttpServletRequest, @Valid @RequestBody registerInstrumentRequest: RegisterInstrumentRequest){
        val instrumentDto = musicianService.registerInstrument(registerInstrumentRequest, req)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleValidationExceptions(ex: InvalidJwtAuthenticationException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["error"] = ex.message.orEmpty()
        return errors
    }
}