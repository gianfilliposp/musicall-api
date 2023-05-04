package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.EventWithJobsDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface EventRepository : JpaRepository<Event, Long> {
    abstract fun findByIdAndUserAndFinalized(id: Long, user: User, finalized: Boolean): Event?
  //    @Query("SELECT ej FROM EventJob ej JOIN ej.instrument i WHERE i.id IN :instrumentIds AND ej.event.finalized = false AND ej.event.eventDate >= :currentDate")
    @Query("SELECT e FROM Event e WHERE e.finalized = false AND e.eventDate >= :currentDate")
    fun findUnfinalizedEventsAfterOrEqual(currentDate: LocalDate): List<Event>
    fun existsByEventDateAndFinalized(eventDate: LocalDate, b: Boolean): Boolean
    @Query("SELECT count(e) > 0 FROM Event e WHERE e.id = :id AND e.user.id = :userId AND e.finalized = false")
    fun existsByIdAndUserIdAndFinalizedFalse(id: Long, userId: Long): Boolean

    @Query("SELECT e.id FROM Event e WHERE e.id = :id AND e.user.id = :userId AND e.finalized = false")
    fun findIdByIdAndUserIdAndFinalizedFalse(id: Long, userId: Long): Long?
}
