package com.example.authenticationservice.dto

import com.example.authenticationservice.model.EventJob

data class EventJobDto (
        val id: Long,
        val fkEvento : Long,
        val instrumentName : String,
        val payment : Double?,
        val isAvailable: Boolean?
) {
    constructor(eventJob: EventJob): this(
            id = eventJob.id,
            fkEvento = eventJob.event.id,
            instrumentName = eventJob.instrument.name,
            payment = eventJob.payment,
            isAvailable = eventJob.musician == null
    )
}
