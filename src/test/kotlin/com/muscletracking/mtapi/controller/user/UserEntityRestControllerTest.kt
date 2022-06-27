package com.muscletracking.mtapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.muscletracking.mtapi.controller.user.UserRestController
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
internal class UserEntityRestControllerTest {
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
    @DisplayName("demo関数は文字列のHello World！を返す")
    fun demo() {
        // 期待値
        val expected = "Hello Github Actions !!"
        // 実行と比較
        mockMvc.perform(get("/demo"))
            .andExpect(status().isOk)
            .andExpect(content().string(expected))
    }

    @Test
    @DisplayName("testDoma関数はユーザーIDryio1010のユーザー情報を返す")
    fun testDoma() {
        mockMvc.perform(get("/test").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("ryio1010"))
            .andExpect(jsonPath("$.name").value("ryo"))
            .andExpect(jsonPath("$.password").value("ryio1010"))
    }
}