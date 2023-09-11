package com.inquirypro.api

import com.inquirypro.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserSignInApi {

    @POST("/api/users/login")
    fun loginUser(@Body user: User): Call<User>

    @GET("/api/users{userId}")
    fun getUserById(@Path("userId") userid: Int): Call<User>
}


