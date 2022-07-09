package com.muscletracking.mtapi.dao.user

import com.muscletracking.mtapi.entity.user.UserEntity
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.Update
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.Result

@ConfigAutowireable
@Dao
interface UserDao {
    @Select
    fun selectById(userId: String): UserEntity?

    @Insert
    fun insertNewUser(newUser: UserEntity): Int

    @Update
    fun updateUser(updateUser: UserEntity): Int
}