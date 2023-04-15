package com.example.authenticationservice.model

import com.example.authenticationservice.parameters.CreateEventJobRequest
import com.example.authenticationservice.parameters.CreateEventRequest
import javax.persistence.*

@Entity
data class EventJob (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id", nullable = false)
        val event : Event,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "instrument_id", nullable = false)
        val instrument: Instrument,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "musician_id", nullable = true)
        val musician : Musician? = null
) {
    constructor(event: Event, instrument: Instrument) : this (
            event = event,
            instrument = instrument,
            musician = null
    )
}