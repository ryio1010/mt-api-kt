package com.muscletracking.mtapi.dao.user

import com.muscletracking.mtapi.entity.user.UserEntity
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable

@ConfigAutowireable
@Dao
interface UserDao {
    @Select
    fun selectById(userId: String): UserEntity?

    @Insert
    fun insertNewUser(newUser: UserEntity): Int

    @Update
    fun updateUser(updateUser: UserEntity): Int

    @Delete
    fun deleteUser(deleteUser: UserEntity): Int
}