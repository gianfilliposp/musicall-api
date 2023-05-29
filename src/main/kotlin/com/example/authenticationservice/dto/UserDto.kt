package com.example.authenticationservice.dto

import com.example.authenticationservice.model.User

data class UserDto (
    val id: Long,
    val name: String
) {
    constructor(user: User) : this(
            id = user.id,
            name = user.name
    )

    constructor() : this(
            id = 0,
            name = ""
    )
}
