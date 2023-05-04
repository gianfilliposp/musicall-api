package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class JobRequest (
    @JsonProperty("instrumentId") val instrumentId: Long,
    @JsonProperty("quantity") @field:Min(1) val quantity: Int,
    @JsonProperty("payment") @field:Min(10) val payment: Double
) {
}