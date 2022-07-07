package com.muscletracking.mtapi.controller.user

import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.entity.user.UserResponse
import com.muscletracking.mtapi.exception.DuplicateIdException
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

    lateinit var response: UserResponse

    @PostMapping("/add")
    fun addNewUser(@ModelAttribute userForm: UserForm): UserResponse {
        // userId重複チェック
        userService.getUserById(userForm.userId) ?: run {
            throw DuplicateIdException()
        }
        // ユーザー新規登録
        val addUser = UserEntity()
        modelMapper.map(userForm, addUser)
        userService.addNewUser(addUser)

        // 登録ユーザーの取得
        val addedUser = userService.getUserById(userForm.userId)
        addedUser?.let {
            response = UserResponse(it.userId, it.userName, it.password)
        }

        // response作成
        return response
    }
}