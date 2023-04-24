package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CreateJobRequestRequest (
    @JsonProperty("fkEvent") @NotNull val fkEvent: Long,
    @JsonProperty("fkMusician") @NotNull val fkMusician: Long
)
