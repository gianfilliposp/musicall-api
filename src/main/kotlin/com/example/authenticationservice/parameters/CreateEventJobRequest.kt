package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateEventJobRequest(
        @JsonProperty("fkEvent") @field:NotNull(message = "O ID do evento não pode ser nulo") val fkEvent: Long?,
        @JsonProperty("jobs") @field:NotNull(message = "A lista de trabalhos não pode ser nula") val jobs: List<JobRequest>?
)
