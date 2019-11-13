package com.joao.domain.entity

data class RequestError(
    val status: Int,
    val message: String
)