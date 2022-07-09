package com.muscletracking.mtapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.truth.Truth.assertThat
import com.muscletracking.mtapi.controller.user.UserRestController
import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.exception.DuplicateIdException
import com.muscletracking.mtapi.exception.ExceptionResponse
import com.muscletracking.mtapi.service.user.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserRestController::class)
internal class UserRestControllerTest() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var mockService: UserService

    @MockBean
    lateinit var modelMapper: ModelMapper

    lateinit var userForm: UserForm

    @BeforeEach
    fun setUp() {
        userForm = UserForm(userId = "test1", userName = "テストユーザー１", password = "test1pass")
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("addNewUser関数はDBに新規ユーザーを1件登録できる")


    fun addNewUserTest() {
        // expected
        val expected = UserEntity("test1", "テストユーザー１", "test1pass")

        doReturn(expected).`when`(mockService).getUserById(anyString())

        mockMvc.perform(post("/user/add").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value("test1"))
            .andExpect(jsonPath("$.userName").value("テストユーザー１"))
            .andExpect(jsonPath("$.password").value("test1pass"))
    }

    @Test
    @DisplayName("addNewUser関数はuseridが重複する場合、DuplicateUserIdExceptionを発生させる")
    fun addNewUserExceptionTest() {
        // expected
        val expectedErrorCode = "400 BAD_REQUEST"
        val expectedErrorMsg = "Duplicate key detected!!"

        // 無条件で例外を投げる
        doThrow(DuplicateIdException()).`when`(mockService).getUserById(anyString())

        val response = mockMvc.perform(
            post("/user/add").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest).andReturn().response.contentAsString

        val errorResponse = objectMapper.readValue(response, ExceptionResponse::class.java)
        assertThat(errorResponse.errorCode).isEqualTo(expectedErrorCode)
        assertThat(errorResponse.errorMessage).isEqualTo(expectedErrorMsg)
    }
}