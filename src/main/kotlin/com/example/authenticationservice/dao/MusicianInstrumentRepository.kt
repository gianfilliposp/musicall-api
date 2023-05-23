package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.MusicianEventJobDto
import com.example.authenticationservice.model.Instrument
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.MusicianInstrument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface MusicianInstrumentRepository : JpaRepository<MusicianInstrument, Long> {
    abstract fun existsByInstrumentIn(instrumentsOfUser: List<Instrument>): Boolean
    abstract fun findByMusician(musician: Musician): List<MusicianInstrument>
    @Query("SELECT new com.example.authenticationservice.dto.MusicianEventJobDto(m.id, m.user.name, m.cep, 0) FROM MusicianInstrument mi JOIN mi.musician m WHERE mi.instrument.id = :instrumentId")
    fun findMusicianEventJobDtoByInstrumentId(instrumentId: Long): List<MusicianEventJobDto>
}
