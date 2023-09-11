package com.inquirypro.api

import com.inquirypro.model.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuestionApi {
    @GET("/getQuestion/{categoryId}")
    fun getQuestionsByCategoryId(@Path("categoryId") partId: Int): Call<List<Question>>
}