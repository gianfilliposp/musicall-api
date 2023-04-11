package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Id

interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email : String) : Boolean
    fun existsByEmailOrCpfOrTelephone(email: String, cpf: String, telephone: String): Boolean
    fun getUserByEmail(email : String) : User?
    fun getUserByEmailAndType(email : String, type : TypeUserDto) : User?

    fun existsByIdAndUserIsCompleted(id : Long) : Boolean
}