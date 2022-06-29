package com.muscletracking.mtapi.service

import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.repository.user.UserRepository
import com.muscletracking.mtapi.service.user.UserService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UserServiceTest {

    @InjectMockKs
    private var userService: UserService = UserService()

    @MockK
    private var userRepository: UserRepository = UserRepository()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("DBからユーザー検索で正しい値が帰ってくる")
    fun getUserByIdTest() {
        // expected
        val expected = UserEntity(userId = "ryio1010", userName = "ryo", password = "ryio1010")

        val inputUserId: String = "001"
        every { userRepository.getUserById(inputUserId) } returns expected

        // actual
        val actual: UserEntity = userService.getUserById(inputUserId)

        verify(exactly = 1) { userRepository.getUserById(any()) }

        expected.userId `should be equal to` actual.userId
        expected.userName `should be equal to` actual.userName
        expected.password `should be equal to` actual.password

        confirmVerified(userRepository)
    }

    @Test
    @DisplayName("新規ユーザーを1件登録できる")
    fun addNewUserTest() {
        // expected
        val expected = UserEntity(userId = "ryio1010", userName = "ryo", password = "ryio1010")
        every { userRepository.addNewUser(any()) } returns expected

        // actual
        val actual: UserEntity = userService.addNewUser(expected)

        verify(exactly = 1) { userRepository.addNewUser(any()) }

        expected.userId `should be equal to` actual.userId
        expected.userName `should be equal to` actual.userName
        expected.password `should be equal to` actual.password

        confirmVerified(userRepository)
    }
}