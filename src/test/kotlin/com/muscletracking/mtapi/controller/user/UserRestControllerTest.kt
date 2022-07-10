package com.muscletracking.mtapi.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.truth.Truth.assertThat
import com.muscletracking.mtapi.entity.user.UserEntity
import com.muscletracking.mtapi.entity.user.UserForm
import com.muscletracking.mtapi.exception.DuplicateIdException
import com.muscletracking.mtapi.exception.ExceptionResponse
import com.muscletracking.mtapi.exception.NoDataFoundException
import com.muscletracking.mtapi.service.user.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
    @DisplayName("getUserById関数はIDでユーザーを１件取得する")
    fun getByIdTest() {
        // expected
        val expected = UserEntity("test1", "テストユーザー１", "test1pass")

        doReturn(expected).`when`(mockService).getUserById(anyString())

        mockMvc.perform(get("/user/test1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value("test1"))
            .andExpect(jsonPath("$.userName").value("テストユーザー１"))
            .andExpect(jsonPath("$.password").value("test1pass"))
    }

    @Test
    @DisplayName("getUserById関数はIDに紐づくユーザーが存在しない場合、NoDataFoundExceptionを発生させる")
    fun getUserByIdNoDataFoundExceptionTest() {
        val expectedErrorCode = "404 NOT_FOUND"
        val expectedErrorMsg = "No Data Found!!!"

        // 無条件で例外実行
        doThrow(NoDataFoundException()).`when`(mockService).getUserById(anyString())

        val response = mockMvc.perform(
            get("/user/test1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound).andReturn().response.contentAsString

        val errorResponse = objectMapper.readValue(response, ExceptionResponse::class.java)
        assertThat(errorResponse.errorCode).isEqualTo(expectedErrorCode)
        assertThat(errorResponse.errorMessage).isEqualTo(expectedErrorMsg)
    }

    @Test
    @DisplayName("addNewUser関数はDBに新規ユーザーを1件登録できる")
    fun addNewUserTest() {
        // expected
        val expected = UserEntity("test1", "テストユーザー１", "test1pass")

        doReturn(null)
            .doReturn(expected)
            .`when`(mockService)
            .getUserById(anyString())

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

    @Test
    @DisplayName("updateUserInfo関数はユーザー情報を更新できる")
    fun updateUserInfoTest() {
        // expected
        val beforeUpdate = UserEntity("test1", "テストユーザー１", "beforeUpdate")
        val expected = UserEntity("test1", "テストユーザー１", "test1pass")

        doReturn(beforeUpdate)
            .doReturn(expected)
            .`when`(mockService)
            .getUserById(anyString())

        mockMvc.perform(put("/user/update").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value("test1"))
            .andExpect(jsonPath("$.userName").value("テストユーザー１"))
            .andExpect(jsonPath("$.password").value("test1pass"))
    }

    @Test
    @DisplayName("updateUserInfo関数はユーザーIDが存在しない場合、NoDataFoundExceptionを発生させる")
    fun updateUserInfoNoDataFoundExceptionTest() {
        val expectedErrorCode = "404 NOT_FOUND"
        val expectedErrorMsg = "No Data Found!!!"

        // 無条件で例外実行
        doThrow(NoDataFoundException()).`when`(mockService).getUserById(anyString())

        val response = mockMvc.perform(
            get("/user/update").flashAttr("userForm", userForm).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound).andReturn().response.contentAsString

        val errorResponse = objectMapper.readValue(response, ExceptionResponse::class.java)
        assertThat(errorResponse.errorCode).isEqualTo(expectedErrorCode)
        assertThat(errorResponse.errorMessage).isEqualTo(expectedErrorMsg)
    }
}