package com.example.authenticationservice.controller

import com.example.authenticationservice.dto.EventDto
import com.example.authenticationservice.exceptions.InvalidJwtAuthenticationException
import com.example.authenticationservice.exceptions.ParameterException
import com.example.authenticationservice.parameters.CreateEventRequest
import com.example.authenticationservice.parameters.CreateJobEventRequest
import com.example.authenticationservice.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.HashMap
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/org")
class OrganizerController (
        @Autowired private val eventService : EventService
) {
    @PostMapping("/event")
    fun createEvent(req : HttpServletRequest, @Valid @RequestBody createEventRequest: CreateEventRequest) : ResponseEntity<EventDto> {
        val event = eventService.createEvent(createEventRequest, req)

        return ResponseEntity.status(201).body(event)
    }

    /*
    @PostMapping("/event/job")
    fun createEvent(req : HttpServletRequest, @Valid @RequestBody createJobEventRequest: CreateJobEventRequest) : ResponseEntity<EventJobDto> {
    }*/

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleEmptyBodyException(ex: HttpMessageNotReadableException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["request body"] = "Request body is missing or empty"
        return errors
    }
}