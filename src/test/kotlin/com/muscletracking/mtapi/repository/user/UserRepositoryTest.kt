package com.muscletracking.mtapi.repository.user

import com.google.common.truth.Truth
import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.UserEntity
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UserRepositoryTest {

    @InjectMockKs
    private var userRepository: UserRepository = UserRepository()

    @RelaxedMockK
    lateinit var userDao: UserDao

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("ID検索でユーザー情報を1件取得できる")
    fun getUserByIdTest() {
        val expected = UserEntity(userId = "ryio1010", userName = "ryo", password = "ryio1010")

        every { userDao.selectById(any()) } returns expected

        val actual = userRepository.getUserById("001")

        verify(exactly = 1) { userRepository.getUserById(any()) }

        actual?.let {
            Truth.assertThat(it.userId).isEqualTo(expected.userId)
            Truth.assertThat(it.userName).isEqualTo(expected.userName)
            Truth.assertThat(it.password).isEqualTo(expected.password)
        }
        confirmVerified(userDao)
    }

    @Test
    @DisplayName("新規ユーザーを1件登録できる")
    fun addNewUserTest() {
        val arg = UserEntity(userId = "test1", userName = "testUser", password = "test1")
        every { userDao.insertNewUser(any()) } returns 1

        userRepository.addNewUser(arg)

        verify(exactly = 1) { userRepository.addNewUser(any()) }

        confirmVerified(userDao)
    }

    @Test
    @DisplayName("ユーザー情報を１件更新できる")
    fun updateUserTest() {
        val arg = UserEntity(userId = "test1", userName = "testUser", password = "test1")
        every { userDao.updateUser(any()) } returns 1

        userRepository.updateUser(arg)

        verify(exactly = 1) { userRepository.updateUser(any()) }

        confirmVerified(userDao)
    }

    @Test
    @DisplayName("ユーザーを削除できる")
    fun deleteUserTest() {
        val arg = UserEntity(userId = "test1", userName = "testUser", password = "test1")
        every { userDao.deleteUser(any()) } returns 1

        userRepository.deleteUser(arg)

        verify(exactly = 1) { userRepository.deleteUser(any()) }

        confirmVerified(userDao)
    }

}