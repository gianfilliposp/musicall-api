package com.example.authenticationservice.model

import com.example.authenticationservice.parameters.RegisterMusicianRequest
import javax.persistence.*

@Entity
data class Musician(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", nullable = false)
    val fkUser: User,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val cep: String
){
    constructor() : this(
        fkUser = User(),
        description = "",
        cep = ""

    )

    constructor(registerMusicianRequest: RegisterMusicianRequest, user: User) : this(
        fkUser = user,
        description = registerMusicianRequest.description!!,
        cep = registerMusicianRequest.cep!!
    )
}