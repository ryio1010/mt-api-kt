package com.muscletracking.mtapi.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(DuplicateIdException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDuplicateIdException(ex: DuplicateIdException, request: WebRequest): ResponseEntity<Any> {
        val response = ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), "キーが重複しています。")
        return super.handleExceptionInternal(ex, response, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request)
    }
}