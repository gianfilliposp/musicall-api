package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class CreateJobEventRequest (
        @JsonProperty("fkEvent") @field:NotBlank val fkEvent : Long,
        @JsonProperty("fkInstrument") @field:NotBlank val fkInstrument : Long,
        @JsonProperty("fkUser") @field:NotBlank val fkUser : Long
) {
}