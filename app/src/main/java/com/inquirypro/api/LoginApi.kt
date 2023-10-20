package com.inquirypro.api


import com.inquirypro.model.login.LoginRequest
import com.inquirypro.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/v2/login/login-email")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/v2/login/login-with-google")
    fun loginWithGoogle(@Body loginRequest: LoginRequest): Call<LoginResponse>


}