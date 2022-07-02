package com.muscletracking.mtapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.muscletracking.mtapi.controller.user.UserRestController
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.exception.ApiExceptionHandler
import com.muscletracking.mtapi.exception.DuplicateIdException
import com.muscletracking.mtapi.exception.ExceptionResponse
import com.muscletracking.mtapi.service.user.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.apache.catalina.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@AutoConfigureMockMvc
internal class UserRestControllerTest {
    @InjectMockKs
    lateinit var controller: UserRestController

    @MockK
    lateinit var mockService: UserService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var userForm: UserForm

    @BeforeEach
    fun setUp() {
        userForm = UserForm(userId = "test3", userName = "テストユーザー１", password = "test1pass")
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("addNewUser関数はDBに新規ユーザーを1件登録できる")
    fun addNewUserTest() {
        mockMvc.perform(post("/user/add").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value("test1"))
            .andExpect(jsonPath("$.userName").value("テストユーザー１"))
            .andExpect(jsonPath("$.password").value("test1pass"))
    }

    @Test
    @DisplayName("addNewUser関数はuseridが重複する場合、DuplicateUserIdExceptionを発生させる")
    fun addNewUserExceptionTest() {
        val mockMvcForException =
            MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ApiExceptionHandler()).build()

        // 無条件で例外を投げる
        every { mockService.getUserById(any()) } throws DuplicateIdException()
        every { mockService.addNewUser(any()) } throws DuplicateIdException()

        val response = mockMvcForException.perform(
            post("/user/add").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest).andReturn().response.contentAsString

        val errorResponse = objectMapper.readValue(response, ExceptionResponse::class.java)
        errorResponse.errorCode `should be equal to` "400 BAD_REQUEST"
        errorResponse.errorMessage `should be equal to` "Duplicate key detected!!"
    }
}