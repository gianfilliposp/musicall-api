package com.example.authenticationservice.controller

import com.example.authenticationservice.dto.UserDto
import com.example.authenticationservice.parameters.DeleteUserRequest
import com.example.authenticationservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/usr")
class UserController (
        @Autowired private val userService : UserService
) {
//    @DeleteMapping
//    fun deleteUser(req: HttpServletRequest, @RequestBody @Valid deleteUserRequest: DeleteUserRequest) : ResponseEntity<Void> {
//        val userDto = userService.deleteUser(req, deleteUserRequest)
//
//        return ResponseEntity.status(201).build()
//    }
}