package com.inquirypro.api

import retrofit2.Call
import retrofit2.http.POST

interface LogoutApi {

    @POST("/api/v1/logout")
    fun logout(): Call<Unit>

}