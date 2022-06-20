package com.muscletracking.mtapi.service.user

import com.muscletracking.mtapi.entity.user.User
import com.muscletracking.mtapi.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun getUserById(userId: String): User {
        return userRepository.getUserById(userId)
    }
}