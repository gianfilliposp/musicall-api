package com.example.authenticationservice.parameters

import com.example.authenticationservice.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.*

data class CreateEventRequest (
        @JsonProperty("name") @field:NotBlank val name: String,
        @JsonProperty("local") @field:NotBlank val local: String,

        @JsonProperty("eventDate")
        @field:DateTimeFormat(pattern = "yyyy/MM/dd")
        @field:Future(message = "Event date must be in the future")
        val eventDate: LocalDate,

        @JsonProperty("durationHours") @field:NotNull @field:Positive val durationHours: Int,
        @JsonProperty("salary") @field:NotNull @field:Positive val salary : Float
) {
}