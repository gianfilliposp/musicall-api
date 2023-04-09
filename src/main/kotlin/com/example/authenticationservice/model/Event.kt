package com.example.authenticationservice.model

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.parameters.CreateEventRequest
import com.example.authenticationservice.parameters.RegisterUserRequest
import com.sun.jdi.request.EventRequest
import org.mindrot.jbcrypt.BCrypt
import javax.persistence.*

@Entity
class Event
  (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0, /* The id value will be generated by JPA */

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val local: String,

        @Column(nullable = false)
        val eventDate: String,

        @Column(nullable = false)
        val duracao: String,

        @Column(nullable = false)
        val qtdConvidados: String,

        @Column(nullable = false)
        val salario: String

    ) {
        constructor(
            createEventRequest: CreateEventRequest
        ) : this(
            name =  createEventRequest.name,
            local =  createEventRequest.local,
            eventDate =  createEventRequest.eventDate,
            duracao =  createEventRequest.duracao,
            qtdConvidados =  createEventRequest.qtdConvidados,
            salario =  createEventRequest.salario
        )
    }
