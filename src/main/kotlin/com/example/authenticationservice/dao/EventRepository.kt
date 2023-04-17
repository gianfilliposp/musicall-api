package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.EventWithJobsDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface EventRepository : JpaRepository<Event, Long> {
    abstract fun existsByEventDateAndFinalized(eventDate: LocalDate, finalized: Boolean): Boolean
    abstract fun findByIdAndUserAndFinalized(id: Long, user: User, finalized: Boolean): Event?
    @Query("SELECT e FROM Event e WHERE e.finalized = false AND e.eventDate >= :currentDate")
    fun findUnfinalizedEventsAfterOrEqual(currentDate: LocalDate): List<Event>
}
