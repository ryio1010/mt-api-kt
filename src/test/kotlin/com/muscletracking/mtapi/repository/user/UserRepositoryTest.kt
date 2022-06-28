package com.muscletracking.mtapi.repository.user

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.UserEntity
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

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
    fun getUserById() {
        val expected = UserEntity(id = "ryio1010", userName = "ryo", password = "ryio1010")

        every { userDao.selectById(any()) } returns expected

        val actual = userRepository.getUserById("001")

        verify(exactly = 1) { userRepository.getUserById(any()) }

        expected.id `should be equal to` actual.id
        expected.userName `should be equal to` actual.userName
        expected.password `should be equal to` actual.password

        confirmVerified(userDao)
    }

    @Test
    @DisplayName("新規ユーザーを1件登録できる")
    fun addNewUser() {
        val expected = UserEntity(id = "test1", userName = "testUser", password = "test1")
        every { userDao.insertNewUser(any()).entity } returns expected

        val actual = userRepository.addNewUser(expected)

        verify(exactly = 1) { userRepository.addNewUser(any()) }

        expected.id `should be equal to` actual.id
        expected.userName `should be equal to` actual.userName
        expected.password `should be equal to` actual.password

        confirmVerified(userDao)
    }
}