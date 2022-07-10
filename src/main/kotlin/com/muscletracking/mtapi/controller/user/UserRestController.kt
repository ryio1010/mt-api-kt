package com.muscletracking.mtapi.controller.user

import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.entity.user.UserResponse
import com.muscletracking.mtapi.exception.DuplicateIdException
import com.muscletracking.mtapi.exception.NoDataFoundException
import com.muscletracking.mtapi.service.user.UserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserRestController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var modelMapper: ModelMapper

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: String): ResponseEntity<UserResponse> {
        // ユーザー検索
        val user = userService.getUserById(userId) ?: throw NoDataFoundException()

        val response = UserResponse(user.userId, user.userName, user.password)

        // response
        return ResponseEntity<UserResponse>(response, HttpStatus.OK)
    }

    @PostMapping("/add")
    fun addNewUser(@ModelAttribute userForm: UserForm): ResponseEntity<UserResponse> {
        // userId重複チェック
        if (userService.getUserById(userForm.userId) != null) {
            throw DuplicateIdException()
        }

        // ユーザー新規登録
        val addUser = UserEntity()
        modelMapper.map(userForm, addUser)
        userService.addNewUser(addUser)

        // 登録ユーザーの取得
        val addedUser = userService.getUserById(userForm.userId) ?: throw NoDataFoundException()
        val response = UserResponse(addedUser.userId, addedUser.userName, addedUser.password)

        // response作成
        return ResponseEntity<UserResponse>(response, HttpStatus.OK)
    }

    @PutMapping("/update")
    fun updateUserInfo(@ModelAttribute userForm: UserForm): ResponseEntity<UserResponse> {
        val target = userService.getUserById(userForm.userId) ?: throw NoDataFoundException()

        // update
        target?.let {
            it.userId = userForm.userId
            it.userName = userForm.userName
            it.password = userForm.password

            userService.updateUser(it)
        }

        val updated = userService.getUserById(userForm.userId) ?: throw NoDataFoundException()
        val response = UserResponse(updated.userId, updated.userName, updated.password)

        return ResponseEntity<UserResponse>(response, HttpStatus.OK)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: String): ResponseEntity<Boolean> {
        val target = userService.getUserById(userId) ?: throw NoDataFoundException()
        userService.deleteUser(target)

        val deleted = userService.getUserById(userId)
        if (deleted != null) {
            throw Exception()
        }
        return ResponseEntity<Boolean>(true, HttpStatus.OK)
    }
}