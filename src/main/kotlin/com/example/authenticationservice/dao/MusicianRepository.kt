package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.model.Musician
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import javax.transaction.Transactional


interface MusicianRepository : JpaRepository<Musician, Long> {
    fun existsByUser(user: User) : Boolean
    fun getByUser(user: User): Musician?

    @Query("SELECT m.cep FROM Musician m WHERE m.user.id = :userId")
    fun findCepByUserId(userId: Long): String?
    @Query("SELECT m FROM Musician m WHERE m.user.id = :id")
    fun getMusicianByUserId(id: Long): Musician?

}
