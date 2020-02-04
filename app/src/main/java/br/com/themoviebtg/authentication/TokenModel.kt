package br.com.themoviebtg.authentication

class TokenModel(
    val success: Boolean,
    val expires_at: String?,
    val request_token: String?

)