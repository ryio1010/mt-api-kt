package com.muscletracking.mtapi.repository.user

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
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

        verify { userRepository.getUserById("001") }
        assertEquals(expected.id, actual.id)
        assertEquals(expected.userName, actual.userName)
        assertEquals(expected.password, actual.password)
    }
}