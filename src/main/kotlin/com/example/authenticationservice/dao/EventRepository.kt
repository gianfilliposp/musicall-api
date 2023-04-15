package com.example.authenticationservice.dao

import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface EventRepository : JpaRepository<Event, Long> {
    abstract fun existsByEventDateAndFinalized(eventDate: LocalDate, finalized: Boolean): Boolean
    abstract fun findByIdAndCreatorEventAndFinalized(id: Long, creatorEvent: User, finalized: Boolean): Event?
}