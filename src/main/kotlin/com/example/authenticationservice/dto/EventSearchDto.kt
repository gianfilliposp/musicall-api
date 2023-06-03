package com.example.authenticationservice.dto

import org.springframework.data.geo.Distance
import java.time.LocalDate

data class EventSearchDto (
    val id:Long,
    val imageUrl:String,
    val eventDate:LocalDate,
    var cep:String,
    var distance: Int = 0
) {

    constructor() : this(
        id = 0,
        imageUrl = "",
        eventDate = LocalDate.now(),
        cep = "",
        distance = 0
    )

    constructor(id: Long, imageUrl: String, eventDate: LocalDate, cep: String) : this(
        id = 0,
        imageUrl = imageUrl,
        eventDate = LocalDate.now(),
        cep = cep,
        distance = 0
    )
}