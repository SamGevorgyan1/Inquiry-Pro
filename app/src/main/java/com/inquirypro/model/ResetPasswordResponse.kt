package com.inquirypro.model

data class ResetPasswordResponse(
    val user: User,
    val message: String,
)