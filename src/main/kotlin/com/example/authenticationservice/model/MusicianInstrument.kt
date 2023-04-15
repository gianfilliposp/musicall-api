package com.example.authenticationservice.model

import com.example.authenticationservice.parameters.CreateEventRequest
import java.time.LocalDate
import javax.persistence.*

@Entity
data class MusicianInstrument (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musician_id")
    val musician : Musician,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id")
    val instrument: Instrument
) {
}