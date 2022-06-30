package com.muscletracking.mtapi.service.user

import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.repository.user.UserRepository
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun getUserById(userId: String): UserEntity? {
        return userRepository.getUserById(userId)
    }

    fun addNewUser(newUser: UserEntity): UserEntity {
        return userRepository.addNewUser(newUser)
    }
}