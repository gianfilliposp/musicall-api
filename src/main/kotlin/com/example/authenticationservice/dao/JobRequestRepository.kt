package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.DeleteJobRequestDto
import com.example.authenticationservice.model.JobRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface JobRequestRepository: JpaRepository<JobRequest, Long> {
    @Query("SELECT COUNT(j) > 0 FROM JobRequest j WHERE j.musician.id = :musicianId AND j.eventJob.event.id = :eventId")
    fun existsByMusicianIdAndEventId(musicianId: Long, eventId: Long): Boolean

    @Query("""
        SELECT new com.example.authenticationservice.dto.DeleteJobRequestDto(j.id, j.organizerConfirmed)
            FROM JobRequest j 
                WHERE j.eventJob.id = :fkEventJob 
                    AND j.musician.id = :musicianId
     """
    )
    fun findIdAndOrganizerConfirmedByEventJobIdAndMusicianId(fkEventJob: Long, musicianId: Long): DeleteJobRequestDto?

    @Query("SELECT j.musician.user.id FROM JobRequest j WHERE j.id = :id AND j.eventJob.event.user.id = :userId AND j.musicianConfirmed = true")
    fun findUserIdByIdAndUserIdAndMusicianConfirmedTrue(id:Long, userId: Long): Long?

    @Modifying
    @Query("UPDATE JobRequest j SET j.organizerConfirmed = true WHERE j.id = :id")
    fun updateOrganizerConfirmedTrueById(id: Long)

    @Modifying
    fun deleteByEventJobId(eventJobId: Long)
    @Modifying
    fun deleteByEventJobEventId(eventId: Long)
}
