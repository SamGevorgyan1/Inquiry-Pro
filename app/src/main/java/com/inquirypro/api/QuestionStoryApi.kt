package com.inquirypro.api

import com.inquirypro.model.Question
import com.inquirypro.model.QuestionResult
import retrofit2.Call
import retrofit2.http.*


interface QuestionStoryApi {

    @GET("/question-story/get-question-stories/{userId}")
    fun getQuestionStoryByUserId(@Path("userId") userId: Int): Call<List<QuestionResult>>

    @GET("/question-story/{questionStoryId}/questions")
    fun getQuestionsFromQuestionStory(@Path("questionStoryId") questionStoryId: Int): Call<List<Question>>

    @GET("/question-story/get-questionStory{questionStoryId}")
    fun getQuestionStoryById(@Path("questionStoryId") questionStoryId: Int): Call<QuestionResult>

    @POST("/question-story/create-question-story")
    fun createQuestionStory(@Body questionResult: QuestionResult): Call<QuestionResult>

    @POST("/question-story/create-question-wit-story")
    fun createQuestionWithStory(@Body questionResult: QuestionResult): Call<QuestionResult>

    @GET("/question-story/get-user-correct-answer-amount/{userId}")
    fun getUserCorrectAnswerAmount(@Path("userId") userId: Int): Call<Int>

    @GET("/question-story/get-user-incorrect-answer-amount/{userId}")
    fun getUserIncorrectAnswerAmount(@Path("userId") userId: Int): Call<Int>

    @GET("/question-story/get-user-correct-answers/{userId}")
    fun getUserCorrectAnswers(@Path("userId") userId: Int):Call<List<QuestionResult>>

    @GET("/question-story/get-user-incorrect-answers/{userId}")
    fun getUserIncorrectAnswers(@Path("userId") userId: Int):Call<List<QuestionResult>>

}