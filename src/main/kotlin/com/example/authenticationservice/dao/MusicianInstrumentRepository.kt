package com.example.authenticationservice.dao

import com.example.authenticationservice.model.MusicianInstrument
import org.springframework.data.jpa.repository.JpaRepository

interface MusicianInstrumentRepository : JpaRepository<MusicianInstrument,Long>{

}