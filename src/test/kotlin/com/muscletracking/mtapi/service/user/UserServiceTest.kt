package com.muscletracking.mtapi.service

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.User
import com.muscletracking.mtapi.repository.user.UserRepository
import com.muscletracking.mtapi.service.user.UserService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.boot.test.context.SpringBootTest
import com.nhaarman.mockitokotlin2.mock

@SpringBootTest
internal class UserServiceTest {

    @InjectMocks
    lateinit var userService: UserService

    @Mock
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("DBからユーザー検索で正しい値が帰ってくる")
    fun getUserByIdTest() {
        // expected
        val expected = User(id = "ryio1010", userName = "ryo", password = "ryio1010")

//        val mock = mock<UserDao> {
//            on { selectById(any()) } doReturn expected
//        }

        // actual
        val inputUserId: String = "ryio1010"
        val actual = userService.getUserById(inputUserId)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.userName, actual.userName)
        assertEquals(expected.password, actual.password)
    }
}