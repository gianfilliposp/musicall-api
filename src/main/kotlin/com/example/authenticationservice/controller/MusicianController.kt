package com.example.authenticationservice.controller

import com.example.authenticationservice.dto.*
import com.example.authenticationservice.parameters.RegisterInstrumentRequest
import com.example.authenticationservice.exceptions.InvalidJwtAuthenticationException
import com.example.authenticationservice.exceptions.ParameterException
import com.example.authenticationservice.parameters.RegisterMusicianRequest
import com.example.authenticationservice.service.MusicianService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.HashMap

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/msc")
@SecurityRequirement(name = "Bearer Authentication")
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
    fun registerInstrument(req: HttpServletRequest, @Valid @RequestBody registerInstrumentRequest: RegisterInstrumentRequest): ResponseEntity<List<InstrumentsDto>> {
        val instrumentDto = musicianService.registerInstrument(registerInstrumentRequest, req)

        return ResponseEntity.status(201).body(instrumentDto)
    }

    @GetMapping("/event")
    fun getEventsByLocation(req: HttpServletRequest) : ResponseEntity<List<EventDto>> {
        val events = musicianService.getEventsByLocation(req)

        return ResponseEntity.status(200).body(events)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleValidationExceptions(ex: InvalidJwtAuthenticationException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["error"] = ex.message.orEmpty()
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> {
        val errors = HashMap<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage ?: "Error"
        }
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParameterException::class)
    fun handleValidationExceptions(ex: ParameterException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors[ex.parameter] = ex.message;
        return errors
    }
}