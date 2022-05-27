package com.muscletracking.mtapi.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class UserRestControllerTest {
    private lateinit var controller: UserRestController

    @BeforeEach
    fun setUp() {
        controller = UserRestController()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("demo関数は文字列のHello World！を返す")
    fun demo() {
        // 期待値
        val expected = "Hello World!"
        // 実績値
        val actual: String = controller.demo()
        // 比較
        assertEquals(expected, actual)
    }
}