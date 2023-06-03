package com.example.authenticationservice.dto

import org.springframework.data.geo.Distance
import java.time.LocalDate

data class EventSearchDto (
    val id:Long,
    val imageUrl:String,
    val eventDate:LocalDate,
    var cep:String,
    var distance: Int = 0
){

}