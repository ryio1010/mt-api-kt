package com.muscletracking.mtapi.repository.user

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.User
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class UserRepositoryTest {

    @InjectMockKs
    private var userRepository: UserRepository = UserRepository()

    @RelaxedMockK
    lateinit var userDao : UserDao

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getUserById() {
        val expected = User(id = "ryio1010", userName = "ryo", password = "ryio1010")

        every { userDao.selectById(any()) } returns expected

        val actual = userRepository.getUserById("001")

        verify(exactly = 1) { userRepository.getUserById(any()) }

        expected.id `should be equal to` actual.id
        expected.userName `should be equal to` actual.userName
        expected.password `should be equal to` actual.password

        confirmVerified(userDao)
    }
}