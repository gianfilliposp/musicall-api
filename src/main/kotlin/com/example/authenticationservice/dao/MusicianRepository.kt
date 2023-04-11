package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository


interface MusicianRepository : JpaRepository<Musician, Long> {
}
