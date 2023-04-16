package com.example.authenticationservice.dto

import com.example.authenticationservice.model.User

data class UserDto (
    val id: Long,
    val name: String,
    val last_name: String,
    val email: String
) {
    constructor(user: User) : this(
            id = user.id,
            name = user.name,
            last_name = user.lastName,
            email = user.email
    )

    constructor() : this(
            id = 0,
            name = "",
            last_name = "",
            email = ""
    )
}
