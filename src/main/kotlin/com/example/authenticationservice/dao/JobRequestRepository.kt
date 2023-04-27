package com.example.authenticationservice.dao

import com.example.authenticationservice.model.JobRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JobRequestRepository: JpaRepository<JobRequest, Long> {
    @Query("SELECT COUNT(j) > 0 FROM JobRequest j WHERE j.musician.id = :musicianId AND j.eventJob.id = :fkEventJob")
    fun existsByMusicianIdAndEventJobId(musicianId: Long, fkEventJob: Long): Boolean
}
