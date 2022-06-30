package com.muscletracking.mtapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.muscletracking.mtapi.controller.user.UserRestController
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.service.user.UserService
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
internal class UserRestControllerTest {
    @Autowired
    lateinit var controller: UserRestController

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("testDoma関数はユーザーIDryio1010のユーザー情報を返す")
    fun testDoma() {
        mockMvc.perform(get("/user/test").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("ryio1010"))
            .andExpect(jsonPath("$.name").value("ryo"))
            .andExpect(jsonPath("$.password").value("ryio1010"))
    }

    @Test
    @DisplayName("addNewUser関数はDBに新規ユーザーを1件登録できる")
    fun addNewUserTest() {
        val userForm = UserForm(userId = "test3", userName = "テストユーザー１", password = "test1pass")
        mockMvc.perform(post("/user/add").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value("test1"))
            .andExpect(jsonPath("$.userName").value("テストユーザー１"))
            .andExpect(jsonPath("$.password").value("test1pass"))
    }

    @Test
    @DisplayName("addNewUser関数はuseridが重複する場合、DuplicateUserIdExceptionを発生させる")
    fun addNewUserExceptionTest(){
            val mockService = mockk<UserService>()
    }
}