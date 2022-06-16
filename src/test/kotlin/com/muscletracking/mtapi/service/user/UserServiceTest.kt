package com.muscletracking.mtapi.service

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.User
import com.muscletracking.mtapi.repository.user.UserRepository
import com.muscletracking.mtapi.service.user.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
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
        val expected = User(id = "ryio1010", userName = "ryo", password = "ryio1010")

        val inputUserId: String = "001"
        every { userRepository.getUserById(inputUserId) } returns expected

        // actual
        val actual: User = userService.getUserById(inputUserId)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.userName, actual.userName)
        assertEquals(expected.password, actual.password)
    }
}