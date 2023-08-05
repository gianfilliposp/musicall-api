package com.example.authenticationservice.service

import com.example.authenticationservice.dao.JobRequestRepository
import com.example.authenticationservice.dao.NotificationRepository
import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.dto.JobRequestDto
import com.example.authenticationservice.dto.NotificationTypeDto
import com.example.authenticationservice.dto.TypeUserDto
import com.example.authenticationservice.model.JobRequest
import com.example.authenticationservice.model.Notification
import com.example.authenticationservice.model.User
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
        @Autowired private val jwtTokenProvider: JwtTokenProvider,
        @Autowired private val notificationRepository: NotificationRepository,
        @Autowired private val jobRequestRepository: JobRequestRepository,
        @Autowired private val musicianService: MusicianService,
        @Autowired private val organizerService: OrganizerService
) {
    fun deleteUser(req: HttpServletRequest, deleteUserRequest: DeleteUserRequest) {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        if (!BCrypt.checkpw(deleteUserRequest.password!!, user.password)) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nome de usuário ou senha inválidos")

        userRepository.delete(user)
    }

    fun requestEmailReset(req: HttpServletRequest, emailRequest: EmailResetRequest): String {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida.")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        user.newEmail = emailRequest.email!!
        user.confirmationToken = UUID.randomUUID().toString()

        userRepository.save(user)

        return user.confirmationToken
    }

    fun setNewEmail(req: HttpServletRequest, setEmailRequest: SetEmailRequest) {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida")
        val id = jwtTokenProvider.getId(token).toLong()
        val user = userRepository.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        if (user.confirmationToken != setEmailRequest.token) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválido")
        if (user.email == user.newEmail) throw ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado")

        user.email = user.newEmail
        user.confirmationToken = ""
        user.newEmail = ""

        userRepository.save(user)
    }
    fun findJobsNotification(req: HttpServletRequest): List<JobRequestDto> {
        val token  = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida")
        val id = jwtTokenProvider.getId(token).toLong()

        val notification = notificationRepository.findJobRequestDtoByUserId(id)
        if (notification.isEmpty()) throw ResponseStatusException(HttpStatus.NO_CONTENT, "Não há notificação")

        return notification
    }

    fun deleteJobNotification(req: HttpServletRequest, notificationId: Long?) {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException( HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida.")
        val id = jwtTokenProvider.getId(token).toLong()

        val deleteNotificationDto = notificationRepository.findDeleteNotificationDtoByUserIdAndNotificationId(id, notificationId!!, NotificationTypeDto.REQUEST) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada")

        notificationRepository.deleteById(notificationId)
        if (deleteNotificationDto.notificationType == NotificationTypeDto.REQUEST) jobRequestRepository.deleteById(deleteNotificationDto.fkJobRequest)
    }

    fun approveJobRequest(req: HttpServletRequest, jobRequestId: Long?) {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException( HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida.")
        val id = jwtTokenProvider.getId(token).toLong()
        val typeUser = jwtTokenProvider.getType(token)
        if (typeUser == TypeUserDto.ORG) organizerService.approveJobRequest(id, jobRequestId)

    }

    fun findType(req: HttpServletRequest): TypeUserDto {
        val token = jwtTokenProvider.resolveToken(req) ?: throw ResponseStatusException( HttpStatus.FORBIDDEN, "Token JWT do usuário com a função inválida.")
        val id = jwtTokenProvider.getId(token).toLong()
        val typeUser = jwtTokenProvider.getType(token)
        return typeUser
    }
}
