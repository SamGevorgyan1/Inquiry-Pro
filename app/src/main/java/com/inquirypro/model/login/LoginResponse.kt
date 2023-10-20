package com.inquirypro.model.login

import com.google.gson.annotations.SerializedName
import com.inquirypro.model.User

data class LoginResponse(

    @SerializedName("user")
    val user: User?,

    val message: String?,

    val token: String?,

) {
    companion object SharedLoginResponse {
        private var loginResponse: LoginResponse? = null
        fun saveLoginResponse(response: LoginResponse) {
            loginResponse = response
        }

        fun retrieveLoginResponse(): LoginResponse? {
            return loginResponse
        }

        fun retrieverUser(): User? {
            return loginResponse?.user
        }
    }
}