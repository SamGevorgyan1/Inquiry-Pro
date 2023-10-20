package com.inquirypro.api

import com.inquirypro.model.QuestionResult
import retrofit2.Call
import retrofit2.http.*


interface QuestionResultApi {

    @GET("/api/question-result/get-result/{questionResultId}")
    fun getQuestionResultById(@Path("questionResultId") questionResultId: Int): Call<QuestionResult>

    @POST("/api/question-result/create")
    fun createQuestionResult(@Body questionResult: QuestionResult): Call<QuestionResult>

    @GET("/api/question-result/get-user-correct-answer-amount/{userId}")
    fun getUserCorrectAnswerAmount(@Path("userId") userId: Int): Call<Int>

    @GET("/api/question-result/get-user-incorrect-answer-amount/{userId}")
    fun getUserIncorrectAnswerAmount(@Path("userId") userId: Int): Call<Int>

    @GET("/api/question-result/get-user-correct-answers/{userId}")
    fun getUserCorrectAnswers(@Path("userId") userId: Int): Call<List<QuestionResult>>

    @GET("/api/question-result/get-user-incorrect-answers/{userId}")
    fun getUserIncorrectAnswers(@Path("userId") userId: Int): Call<List<QuestionResult>>

    @GET("/api/question-result/get-questionResult-history-subsection/{userId}/{subsectionId}")
    fun getQuestionStoryByUserIdAndPartId(@Path("userId") userId: Int, @Path("subsectionId") subsectionId: Int): Call<List<QuestionResult>>

    @GET("/api/question-result/get-subsection-question-result/{userId}/{subsectionId}")
    fun getAllPartQuestionResult(@Path("userId") userId: Int, @Path("subsectionId") subsectionId: Int) :Call<List<QuestionResult>>
}