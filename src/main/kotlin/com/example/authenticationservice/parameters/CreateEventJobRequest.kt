package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateEventJobRequest (
        @JsonProperty("fkEvent") @field:NotNull val fkEvent : Long?,
        @JsonProperty("jobs") @field:NotNull val jobs : List<JobRequest>?
) {
}