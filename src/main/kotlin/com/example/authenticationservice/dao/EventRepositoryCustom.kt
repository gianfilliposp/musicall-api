package com.example.authenticationservice.dao

import com.example.authenticationservice.dto.EventSearchDto
import com.example.authenticationservice.model.Event
import com.example.authenticationservice.parameters.FilterEventsRequest
import java.time.LocalDate

interface EventRepositoryCustom {
    fun findUnfinalizedEventsAfterOrEqual(filterEventsRequest: FilterEventsRequest, instrumentsId: List<Long>): List<EventSearchDto>
}
