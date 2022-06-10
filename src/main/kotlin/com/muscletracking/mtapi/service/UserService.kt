package com.muscletracking.mtapi.service

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userDao: UserDao

    fun getUserById(userId: String): User {
        return userDao.selectById(userId)
    }
}