package com.muscletracking.mtapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MtApiApplication

fun main(args: Array<String>) {
	runApplication<MtApiApplication>(*args)
}
