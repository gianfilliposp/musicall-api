package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.JobRequestDto
import com.example.authenticationservice.model.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationRepository: JpaRepository<Notification, Long> {
    fun deleteByJobRequestId(jobRequestFk: Long)
    @Query("""
        select new com.example.authenticationservice.dto.JobRequestDto(notification.id, notification.jobRequest)
            from Notification notification
                where notification.user.id = :userId
    """)
    fun findJobRequestDtoByUserId(userId: Long): List<JobRequestDto>
}
