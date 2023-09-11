package com.inquirypro.repository

import com.inquirypro.api.RetrofitService
import com.inquirypro.model.Question
import retrofit2.awaitResponse

interface QuestionRepository {

    suspend fun getQuestionsByCategoryId(partId: Int): List<Question>?

}

class QuestionRepositoryImpl :QuestionRepository {

    override suspend fun getQuestionsByCategoryId(partId: Int): List<Question>? {
        val response = RetrofitService.getQuestionApiService().getQuestionsByCategoryId(partId).awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }
}