package com.inquirypro.api

import com.inquirypro.model.Part
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PartApi {

    @GET("/api/parts/byCategory/{categoryId}")
    fun getPartsByCategoryId(@Path("categoryId") categoryId: Int): Call<List<Part>>

    @GET("/api/parts/get-part{partId}")
    fun getPartById(@Path("partId") partId:Int):Call<Part>

}