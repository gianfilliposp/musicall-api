package com.example.authenticationservice.controller

import com.example.authenticationservice.exceptions.InvalidJwtAuthenticationException
import com.example.authenticationservice.parameters.EmailResetRequest
import com.example.authenticationservice.service.UserService
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@ExtendWith(MockKExtension::class)
    class UserControllerTest {
    private val userService = mockk<UserService>()
    private val userController = UserController(userService)

    @Test
    fun `test approveJobRequest method`() {

        val request = mockk<HttpServletRequest>()


        val id = 123L


        every { userService.approveJobRequest(request, id) } just runs


        val responseEntity: ResponseEntity<Void> = userController.approveJobRequest(request, id)


        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        verify { userService.approveJobRequest(request, id) }
        confirmVerified(userService)
    }

    @Test
    fun `test requestEmailReset method`() {

        val request = mockk<HttpServletRequest>()


        val setEmailRequest = mockk<EmailResetRequest>()

        val resetToken = "dummyToken"
        every { userService.requestEmailReset(request, setEmailRequest) } returns resetToken


        val responseEntity: ResponseEntity<Void> = userController.requestEmailReset(request, setEmailRequest)


        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        verify { userService.requestEmailReset(request, setEmailRequest) }
        confirmVerified(userService)
    }

    @Test
    fun `test handleValidationExceptions method`() {

        val exceptionMessage = "Invalid JWT authentication"
        val exception = InvalidJwtAuthenticationException(exceptionMessage)


        val response: Map<String, String> = userController.handleValidationExceptions(exception)


        val expectedResponse = HashMap<String, String>()
        expectedResponse["error"] = exceptionMessage
        assertEquals(expectedResponse, response)
    }


}


