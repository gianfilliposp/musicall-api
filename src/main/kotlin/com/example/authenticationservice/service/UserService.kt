package com.example.authenticationservice.service

import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.dto.UserDto
import com.example.authenticationservice.model.User
import com.example.authenticationservice.parameters.DeleteUserRequest
import com.example.authenticationservice.security.JwtTokenProvider
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@Service
class UserService (
        @Autowired private val userRepository: UserRepository,
        @Autowired private val jwtTokenProvider: JwtTokenProvider
) {
    fun deleteUser(req: HttpServletRequest, deleteUserRequest: DeleteUserRequest) {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        if (!BCrypt.checkpw(deleteUserRequest.password!!, user.password)) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied")

        userRepository.delete(user)
    }

}
