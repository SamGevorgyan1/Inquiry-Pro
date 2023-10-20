package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.ApiService
import com.inquirypro.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface QuestionRepository {

    suspend fun getQuestionsByCategoryId(categoryId: Int): Result<List<Question>>
    suspend fun getQuestionsBySubsectionId(partId: Int): Result<List<Question>>
}

class QuestionRepositoryImpl : QuestionRepository {

    override suspend fun getQuestionsByCategoryId(categoryId: Int): Result<List<Question>> {
        return withContext(Dispatchers.Main) {
            try {
                val response =
                    ApiService.getQuestionApiService().getQuestionsByCategoryId(categoryId)
                        .awaitResponse()
                if (response.isSuccessful) {
                    Result.Success(response.body() ?: emptyList())
                } else {
                    Result.Error("Failed to fetch questions by category")
                }
            } catch (e: Exception) {
                Log.e("QuestionRepository", "Error fetching questions by category", e)
                Result.Error("Network error: ${e.message}")
            }
        }
    }

    override suspend fun getQuestionsBySubsectionId(subsectionId: Int): Result<List<Question>> {
        return withContext(Dispatchers.Main) {
            try {
                val response =
                    ApiService.getQuestionApiService().getQuestionsBySubsectionId(subsectionId)
                        .awaitResponse()
                if (response.isSuccessful) {
                    Result.Success(response.body() ?: emptyList())
                } else {
                    Result.Error("Failed to fetch questions by part")
                }
            } catch (e: Exception) {
                Log.e("QuestionRepository", "Error fetching questions by part", e)
                Result.Error("Network error: ${e.message}")
            }
        }
    }
}
