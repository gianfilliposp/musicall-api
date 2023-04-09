package com.example.authenticationservice.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateEventRequest (
    @JsonProperty("name") @field:NotBlank val name: String,
                                    @JsonProperty("local")@field:NotBlank val local: String,
                                    @JsonProperty("event") @field:NotBlank val eventDate: String,
                                    @JsonProperty("duracao") @field:NotBlank val duracao: String,
                                    @JsonProperty("qtdConvidados") @field:NotBlank val qtdConvidados: String,
                                    @JsonProperty("salario")@field:NotBlank val salario: String
)
