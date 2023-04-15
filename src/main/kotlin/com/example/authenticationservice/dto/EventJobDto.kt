package com.example.authenticationservice.dto

import com.example.authenticationservice.model.EventJob

data class EventJobDto (
        val id: Long,
        val fkEvento : Long,
        val instrumentName : String,
        val salary : Float,
        val isAvailable: Boolean?
) {
    constructor(eventJob: EventJob): this(
            id = eventJob.id,
            fkEvento = eventJob.event.id,
            instrumentName = eventJob.instrument.name,
            salary = eventJob.event.salary,
            isAvailable = eventJob.musician == null
    )
}
