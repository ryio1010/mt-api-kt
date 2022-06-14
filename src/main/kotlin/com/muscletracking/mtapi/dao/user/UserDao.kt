package com.muscletracking.mtapi.dao.user

import com.muscletracking.mtapi.entity.user.User
import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable

@ConfigAutowireable
@Dao
interface UserDao {
    @Select
    fun selectById(userId: String): User
}