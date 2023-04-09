package com.example.authenticationservice.controller

import com.example.authenticationservice.exceptions.InvalidJwtAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.HashMap

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/msc")
class MusicianController {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun home(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        return ResponseEntity.ok("{\"response\":\"Logged in as ${userDetails.username}\"}")
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleValidationExceptions(ex: InvalidJwtAuthenticationException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["error"] = ex.message.orEmpty()
        return errors
    }
}