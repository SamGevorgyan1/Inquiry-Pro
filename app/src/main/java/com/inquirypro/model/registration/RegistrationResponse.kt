package com.inquirypro.model.registration

import com.google.gson.annotations.SerializedName
import com.inquirypro.model.User

data class RegistrationResponse(
    @SerializedName("user")
    val user: User,
    val token: String,
    val message:String
)