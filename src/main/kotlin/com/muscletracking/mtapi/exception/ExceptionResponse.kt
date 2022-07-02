package com.muscletracking.mtapi.exception

import lombok.Data

@Data
data class ExceptionResponse(
    val errorCode:String,
    val errorMessage:String
)