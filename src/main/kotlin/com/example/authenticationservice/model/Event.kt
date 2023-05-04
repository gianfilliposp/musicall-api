package com.example.authenticationservice.model

import com.example.authenticationservice.parameters.CreateEventRequest
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Event
  (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn (name = "user_id", nullable = false)
        val user: User,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var cep: String,

        @Column(nullable = false)
        var number: Int,

        @Column(nullable = false)
        var complement: String,
        @Column(nullable = false)
        var eventDate: LocalDate,

        @Column(nullable = false)
        var startHour: Time,

        @Column(nullable = false)
        var durationHours : Int,

        @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
        val eventJob: MutableList<EventJob> = mutableListOf()
    ) {
        @Column(nullable = false)
        var finalized = false

        constructor(
            createEventRequest: CreateEventRequest, creator: User
        ) : this(
                user = creator,
                name =  createEventRequest.name!!,
                cep =  createEventRequest.cep!!,
                number = createEventRequest.number!!,
                complement = createEventRequest.complement!!,
                eventDate =  createEventRequest.eventDate!!,
                startHour =  createEventRequest.startHour!!,
                durationHours =  createEventRequest.durationHours!!
        )

        constructor(id: Long) : this (
            id = id,
            user = User(),
            name =  "",
            cep =  "",
            number = 0,
            complement = "",
            eventDate =  LocalDate.now(),
            startHour = Time(0),
            durationHours =  0
        )
        constructor() : this (
                user = User(),
                name =  "",
                cep =  "",
                number = 0,
                complement = "",
                eventDate =  LocalDate.now(),
                startHour = Time(0),
                durationHours =  0
        )
    }
