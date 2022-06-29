package com.muscletracking.mtapi.controller.user

import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.entity.user.UserResponse
import com.muscletracking.mtapi.service.user.UserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserRestController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var modelMapper: ModelMapper

    @GetMapping("/test")
    fun testDoma(): UserResponse {
        val user = userService.getUserById("ryio1010")
        println(user)
        return UserResponse(user.userId, user.userName, user.password)
    }

    @PostMapping("/add")
    fun addNewUser(@ModelAttribute userForm: UserForm): UserResponse {
        val addUser = UserEntity(regId = userForm.userId, updId = userForm.userId)
        modelMapper.map(userForm, addUser)

        val addedUser = userService.addNewUser(addUser)
        val response = UserResponse(addedUser.userId, addUser.userName, addUser.password)
        return response
    }
}