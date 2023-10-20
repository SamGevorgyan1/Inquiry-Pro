package com.inquirypro.api

import com.inquirypro.model.ResetPasswordResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ResetPasswordApi {

    @POST("/api/reset/password/save-reset-token/{email}")
    fun resetPassword(@Path("email") email: String): Call<ResetPasswordResponse>

    @POST("/api/reset/password/set-new-password/{newPassword}/{email}/{resetToken}")
    fun setNewPassword(
        @Path("newPassword") newPassword: String, @Path("email") email: String, @Path("resetToken") resetToken: String
    ): Call<ResetPasswordResponse>
}