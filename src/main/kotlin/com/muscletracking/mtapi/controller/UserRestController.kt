package com.muscletracking.mtapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController {
    @GetMapping("/demo")
    fun demo():String {
        return "Hello Github Actions !!"
    }
}