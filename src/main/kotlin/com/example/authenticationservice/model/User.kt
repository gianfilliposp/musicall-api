package com.example.authenticationservice.model

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.parameters.RegisterUserRequest
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        val type: TypeUserDto,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val lastName: String,

        @Column(nullable = false, unique = true)
        val cpf: String,

        @Column(nullable = false)
        val birthDate: LocalDate,

        @Column(nullable = false, unique = true)
        val telephone: String,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        var password: String,

        @Column(nullable = false)
        var confirmationToken: String
) {
    var numberOfEvents = 0
    var isConfirmed = false
    var isPasswordResetRequested = false
    var passwordResetToken = ""

    constructor(
            registerUserRequest: RegisterUserRequest, token: String
    ) : this(
            type = registerUserRequest.type!!,
            name = registerUserRequest.name!!,
            lastName = registerUserRequest.lastName!!,
            cpf = registerUserRequest.cpf!!,
            birthDate = registerUserRequest.birthDate!!,
            telephone = registerUserRequest.telephone!!,
            email = registerUserRequest.email!!,
            password = BCrypt.hashpw(registerUserRequest.password!!, BCrypt.gensalt()),
            confirmationToken = token
    )

    constructor() : this(
            type = TypeUserDto.NONE,
            name = "",
            lastName = "",
            cpf = "",
            birthDate = LocalDate.now(),
            telephone = "",
            email = "",
            password = "",
            confirmationToken = ""
    )
}
