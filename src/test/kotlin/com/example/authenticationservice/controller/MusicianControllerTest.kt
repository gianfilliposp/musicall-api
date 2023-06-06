package com.example.authenticationservice.controller

import com.example.authenticationservice.dto.*
import com.example.authenticationservice.exceptions.InvalidJwtAuthenticationException
import com.example.authenticationservice.exceptions.ParameterException
import com.example.authenticationservice.parameters.FilterEventsRequest
import com.example.authenticationservice.parameters.RegisterInstrumentRequest
import com.example.authenticationservice.parameters.RegisterMusicianRequest
import com.example.authenticationservice.parameters.UpdateMusicianRequest
import com.example.authenticationservice.service.MusicianService
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate

import javax.servlet.http.HttpServletRequest


class MusicianControllerTest {

    private lateinit var musicianService: MusicianService
    private lateinit var musicianController: MusicianController

    @BeforeEach
    fun setup() {
        musicianService = mockk(relaxed = true)
        musicianController = MusicianController(musicianService)
    }

    @Test
    fun testRegisterMusician() {
        // Arrange
        val request = mockk<HttpServletRequest>(relaxed = true)
        val registerMusicianRequest = RegisterMusicianRequest("John Doe", "john@example.com", "password")
        val musicianDto = MusicianDto(String(), String())
        every { musicianService.registerMusician(any(), any()) } returns musicianDto

        // Act
        val response = musicianController.registerMusician(request, registerMusicianRequest)

        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(musicianDto, response.body)
        verify { musicianService.registerMusician(registerMusicianRequest, request) }
    }

    @Test
    fun testUpdateMusician() {
        // Arrange
        val request = mockk<HttpServletRequest>(relaxed = true)
        val updateMusicianRequest = UpdateMusicianRequest(String(), String(), String())
        every { musicianService.updateMusician(any(), any()) } just Runs

        // Act
        val response = musicianController.updateMusician(request, updateMusicianRequest)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        verify { musicianService.updateMusician(updateMusicianRequest, request) }
    }

    @Test
    fun testFindEventsByLocation() {
        // Arrange
        val request = mockk<HttpServletRequest>(relaxed = true)
        val filterEventsRequest = mockk<FilterEventsRequest>()
        val eventDto1 = mockk<EventSearchDto>()
        val eventDto2 = mockk<EventSearchDto>()
        val events = listOf(eventDto1, eventDto2)
        every { musicianService.getEventsByLocation(any(), any()) } returns events

        // Act
        val response = musicianController.findEventsByLocation(request, filterEventsRequest)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(events, response.body)
        verify { musicianService.getEventsByLocation(filterEventsRequest, request) }
    }

    @Test
    fun testRegisterInstrument() {
        // Arrange
        val request = mockk<HttpServletRequest>(relaxed = true)
        val registerInstrumentRequest = RegisterInstrumentRequest(listOf(5L))
        val instrumentDto1 = InstrumentsDto(1, "Guitar")
        val instrumentDto2 = InstrumentsDto(2, "Bass")
        val instrumentDtos = listOf(instrumentDto1, instrumentDto2)
        every { musicianService.registerInstrument(any(), any()) } returns instrumentDtos

        // Act
        val response = musicianController.registerInstrument(request, registerInstrumentRequest)

        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(instrumentDtos, response.body)
        verify { musicianService.registerInstrument(registerInstrumentRequest, request) }
    }

    @Test
    fun testHandleInvalidJwtAuthenticationException() {
        // Arrange
        val exception = InvalidJwtAuthenticationException("Invalid token")

        // Act
        val response = musicianController.handleValidationExceptions(exception)

        // Assert
        assertEquals(1, response.size)
        assertEquals("Invalid token", response["error"])
    }


    @Test
    fun testHandleParameterException() {
        // Arrange
        val exception = ParameterException("parameterName", "Parameter error")

        // Act
        val response = musicianController.handleValidationExceptions(exception)

        // Assert
        assertEquals(1, response.size)
        assertEquals("Parameter error", response["parameterName"])
    }
}
