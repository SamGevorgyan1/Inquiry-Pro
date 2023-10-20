package com.inquirypro.api

import com.inquirypro.model.History
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryApi {

    @GET("/get-user-history/{userId}")
    fun getUserHistories(@Path("userId") userId: Int): Call<List<History>>

    @GET("/get-histories-user-and-category/{userId}/{subsectionId}")
    fun getHistoriesByUserIdAndSubsectionId(@Path("userId") userId: Int, @Path("subsectionId") subsectionId: Int):Call<List<History>>

}