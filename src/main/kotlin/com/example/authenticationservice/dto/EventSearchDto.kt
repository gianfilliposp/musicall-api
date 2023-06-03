package com.example.authenticationservice.dto

import java.time.LocalDate

data class EventSearchDto (
    val id:Long,
    val imageUrl:String,
    val eventDate:LocalDate
){

}