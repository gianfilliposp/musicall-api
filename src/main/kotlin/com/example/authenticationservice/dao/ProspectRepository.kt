package com.example.authenticationservice.dao

import com.example.authenticationservice.model.Prospect
import org.springframework.data.jpa.repository.JpaRepository

interface ProspectRepository : JpaRepository<Prospect, Long> {
    fun findByEmail(email: String): Prospect?
}
