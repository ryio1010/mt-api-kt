package com.muscletracking.mtapi.service.user

import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun getUserById(userId: String): UserEntity? {
        return userRepository.getUserById(userId)
    }

    fun addNewUser(newUser: UserEntity): Int {
        return userRepository.addNewUser(newUser)
    }

    fun updateUser(updateInfo: UserEntity): Int {
        return userRepository.updateUser(updateInfo)
    }

    fun deleteUser(deleteUser: UserEntity): Int {
        return userRepository.deleteUser(deleteUser)
    }
}