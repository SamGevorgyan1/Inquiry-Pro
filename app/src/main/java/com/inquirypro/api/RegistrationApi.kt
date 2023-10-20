package com.inquirypro.api

import com.inquirypro.model.registration.ConfirmationResponse
import com.inquirypro.model.registration.RegistrationRequest
import com.inquirypro.model.registration.RegistrationRequestGoogle
import com.inquirypro.model.registration.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegistrationApi {

    @POST("/api/v1/registration/register")
    fun registerWithEmail(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @POST("api/v1/registration/register-google")
    fun registerWithGoogle(@Body request: RegistrationRequestGoogle): Call<RegistrationResponse>

    @GET("/api/v1/registration/confirm/{token}/{confirmCode}")
    fun confirmToken(@Path("token") token: String, @Path("confirmCode") confirmCode: String): Call<ConfirmationResponse>

}