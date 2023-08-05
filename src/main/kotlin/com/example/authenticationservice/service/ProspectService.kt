package com.example.authenticationservice.service

import com.example.authenticationservice.dao.ProspectRepository
import com.example.authenticationservice.dao.UserRepository
import com.example.authenticationservice.model.Prospect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProspectService (
        @Autowired private val prospectRepository: ProspectRepository
){
    fun findProspect(email: String?): Prospect {
        val prospect = prospectRepository.findByEmail(email!!)  ?: throw ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi encontrado")

        return prospect
    }

}
