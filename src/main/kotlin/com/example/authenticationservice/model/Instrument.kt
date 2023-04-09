package com.example.authenticationservice.model

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.parameters.RegisterUserRequest
import org.mindrot.jbcrypt.BCrypt
import javax.persistence.*

@Entity
class Instrument (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0, /* The id value will be generated by JPA */

        @Column(nullable = false)
        val name: String
)
     {
    }
