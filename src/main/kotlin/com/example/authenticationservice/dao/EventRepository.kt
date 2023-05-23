package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.EventWithJobsDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface EventRepository : JpaRepository<Event, Long> {
    abstract fun findByIdAndUserAndFinalized(id: Long, user: User, finalized: Boolean): Event?
  //    @Query("SELECT ej FROM EventJob ej JOIN ej.instrument i WHERE i.id IN :instrumentIds AND ej.event.finalized = false AND ej.event.eventDate >= :currentDate")
  @Query(value = """
    SELECT * FROM event_job
    JOIN event
    ON event_job.event_id = event.id
    WHERE event.finalized = false AND event.event_date >= :currentDate
    AND event_job.instrument_id IN :instrumentIds
    """, nativeQuery = true)
  fun findUnfinalizedEventsAfterOrEqual(@Param("currentDate") currentDate: LocalDate, @Param("instrumentIds") instrumentsId: List<Long>): List<Event>
    fun existsByEventDateAndFinalized(eventDate: LocalDate, b: Boolean): Boolean
    @Query("SELECT count(e) > 0 FROM Event e WHERE e.id = :id AND e.user.id = :userId AND e.finalized = false")
    fun existsByIdAndUserIdAndFinalizedFalse(id: Long, userId: Long): Boolean

    @Query("SELECT e.id FROM Event e WHERE e.id = :id AND e.user.id = :userId AND e.finalized = false")
    fun findIdByIdAndUserIdAndFinalizedFalse(id: Long, userId: Long): Long?
  abstract fun findByIdAndUserIdAndFinalizedFalse(eventId: Long, id: Long): Nothing?
}
