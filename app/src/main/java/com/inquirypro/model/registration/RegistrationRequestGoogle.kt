package com.inquirypro.model.registration

data class RegistrationRequestGoogle(
    val firstName: String,
    val lastName: String,
    val email: String,
    val emailTokenId: String,
)