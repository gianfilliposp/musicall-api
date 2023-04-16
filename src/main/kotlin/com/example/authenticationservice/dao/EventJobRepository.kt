package com.example.authenticationservice.dao

import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.EventJob
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface EventJobRepository : JpaRepository<EventJob, Long> {
    abstract fun existsByIdAndEvent(id: Long, event: Event): Boolean
}
