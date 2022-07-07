package com.muscletracking.mtapi

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Aspect
@Component
class LoggingAspects {
    val logger: Logger = LoggerFactory.getLogger(LoggingAspects::class.java)


    @Before("within(com.muscletracking.mtapi.controller.user.*)")
    fun controllerStartLog(jointPoint: JoinPoint) {
        val string = jointPoint.toString()
        val args = Arrays.toString(jointPoint.args)
        logger.info("Start {}, args {}", string, args)
    }
}