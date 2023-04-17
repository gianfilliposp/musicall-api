package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface MusicianRepository : JpaRepository<Musician, Long> {
    fun existsByUser(user: User) : Boolean
    fun findIdByUser(user: User): Long?

    fun getByUser(user: User): Musician?

    @Query("SELECT m.cep FROM Musician m WHERE m.user.id = :userId")
    fun findCepByUserId(userId: Long): String?
    abstract fun existsByUserId(id: Long): Boolean
}
