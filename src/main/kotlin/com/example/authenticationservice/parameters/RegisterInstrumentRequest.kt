package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RegisterInstrumentRequest (
    @JsonProperty("fkMusician") @field:NotNull val fkMusician: Long,
    @JsonProperty("fkInstrument") @field:NotNull val fkInstrument: List<Long>
)