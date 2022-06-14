package com.muscletracking.mtapi.repository.user

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    @Autowired
    lateinit var userDao: UserDao

    fun getUserById(userId: String): User {
        return userDao.selectById(userId)
    }
}