package com.muscletracking.mtapi.controller

import com.muscletracking.mtapi.entity.UserResponse
import com.muscletracking.mtapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/demo")
    fun demo(): String {
        return "Hello Github Actions !!"
    }

    @GetMapping("/test")
    fun testDoma(): UserResponse {
        val user = userService.getUserById("ryio1010")
        return UserResponse(user.id, user.userName, user.password)
    }
}