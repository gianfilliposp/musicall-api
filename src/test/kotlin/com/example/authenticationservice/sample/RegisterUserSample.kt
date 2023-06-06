package com.example.authenticationservice.sample

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.parameters.RegisterUserRequest
import java.time.LocalDate

object RegisterUserSample {
    fun getRegisterUserSuccess(): RegisterUserRequest = RegisterUserRequest(
        type = TypeUserDto.MSC,
        name = "John Doe",
        cpf = "12345678901",
        birthDate = LocalDate.of(1990, 5, 15),
        telephone = "1234567890",
        email = "johndoe@example.com",
        password = "secretpassword"
    )

    fun getRegisterUserWithInvalidType(): RegisterUserRequest = RegisterUserRequest(
        type = null,
        name = "John Doe",
        cpf = "12345678901",
        birthDate = LocalDate.of(1990, 5, 15),
        telephone = "1234567890",
        email = "johndoe@example.com",
        password = "secretpassword"
    )
}