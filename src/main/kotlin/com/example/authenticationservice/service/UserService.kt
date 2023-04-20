package com.example.authenticationservice.service

import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.parameters.DeleteUserRequest
import com.example.authenticationservice.parameters.EmailResetRequest
import com.example.authenticationservice.parameters.SetEmailRequest
import com.example.authenticationservice.security.JwtTokenProvider
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
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

    fun requestEmailReset(req: HttpServletRequest, emailRequest: EmailResetRequest): String {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        user.newEmail = emailRequest.email!!
        user.confirmationToken = UUID.randomUUID().toString()

        userRepository.save(user)

        return user.confirmationToken
    }

    fun setNewEmail(req: HttpServletRequest, setEmailRequest: SetEmailRequest) {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "User invalid role JWT token.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        if (user.confirmationToken != setEmailRequest.token) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token")
        if (user.email == user.newEmail) throw ResponseStatusException(HttpStatus.CONFLICT, "This is the email already")

        user.email = user.newEmail
        user.confirmationToken = ""
        user.newEmail = ""

        userRepository.save(user)
    }
}
