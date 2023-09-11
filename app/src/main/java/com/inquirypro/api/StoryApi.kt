package com.inquirypro.api

import com.inquirypro.model.Story
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StoryApi {

    @GET("/get-user-story/{userId}")
    fun getUserStories(@Path("userId") userId: Int): Call<List<Story>>

    @POST("/add-question-result")
    fun addQuestionResultToStory(@Body story: Story): Call<Story>


}