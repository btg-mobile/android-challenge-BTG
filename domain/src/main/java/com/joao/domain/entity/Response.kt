package com.joao.domain.entity

sealed class Response<T>
data class SuccessResponse<T>(val body: T) : Response<T>()
data class ErrorResponse<T>(val error: RequestError) : Response<T>()