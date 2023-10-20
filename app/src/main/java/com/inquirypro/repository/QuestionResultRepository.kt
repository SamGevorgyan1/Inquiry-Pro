package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.ApiService
import com.inquirypro.model.QuestionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface QuestionResultRepository {

    suspend fun getQuestionResultById(questionResultId: Int): QuestionResult?

    suspend fun createQuestionResult(questionResult: QuestionResult): QuestionResult?


    suspend fun getUserCorrectAnswers(userId: Int): Result<List<QuestionResult>?>

    suspend fun getUserIncorrectAnswers(userId: Int): Result<List<QuestionResult>?>

    suspend fun getUserCorrectAnswerAmount(userId: Int): Int?

    suspend fun getUserIncorrectAnswerAmount(userId: Int): Int?

    suspend fun getSubsectionQuestionResult(userId: Int, subsectionId: Int): List<QuestionResult>?

    suspend fun getAllPartQuestionResult(userId: Int, subsectionId: Int): List<QuestionResult>?

}

class QuestionResultRepositoryImpl : QuestionResultRepository {


    override suspend fun getQuestionResultById(questionResultId: Int): QuestionResult? {
        val response =
            ApiService.getQuestionStoryApiService().getQuestionResultById(questionResultId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun createQuestionResult(questionResult: QuestionResult): QuestionResult? {

        return withContext(Dispatchers.IO) {
            try {
                val response =
                    ApiService.getQuestionStoryApiService().createQuestionResult(questionResult)
                        .awaitResponse()

                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }


    override suspend fun getUserCorrectAnswers(userId: Int): Result<List<QuestionResult>?> {

        return withContext(Dispatchers.Main) {
            try {

                val response = ApiService.getQuestionStoryApiService().getUserCorrectAnswers(userId)
                    .awaitResponse()

                if (response.isSuccessful) {
                    Result.Success(response.body() ?: emptyList())
                } else {
                    Result.Error("Failed to fetch user correct answers")
                }

            } catch (e: Exception) {
                Log.e("QuestionResultRepo", "Error fetching user correct answer", e)
                Result.Error("Network error: ${e.message}")
            }
        }
    }

    override suspend fun getUserIncorrectAnswers(userId: Int): Result<List<QuestionResult>?> {

        return  withContext(Dispatchers.IO){
            try {
                val response = ApiService.getQuestionStoryApiService().getUserIncorrectAnswers(userId).awaitResponse()

                if (response.isSuccessful){
                    Result.Success(response.body()?: emptyList())
                }else {
                    Result.Error("Failed to fetch user incorrect answers")
                }

            }catch (e:Exception){
                Log.e("QuestionResultRepo", "Error fetching user correct answer", e)
                Result.Error("Network error: ${e.message}")
            }
        }
    }

    override suspend fun getUserCorrectAnswerAmount(userId: Int): Int? {
        val response =
            ApiService.getQuestionStoryApiService().getUserCorrectAnswerAmount(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getUserIncorrectAnswerAmount(userId: Int): Int? {
        val response =
            ApiService.getQuestionStoryApiService().getUserIncorrectAnswerAmount(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getSubsectionQuestionResult(
        userId: Int,
        subsectionId: Int
    ): List<QuestionResult>? {
        val response = ApiService.getQuestionStoryApiService()
            .getQuestionStoryByUserIdAndPartId(userId, subsectionId).awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getAllPartQuestionResult(
        userId: Int,
        subsectionId: Int
    ): List<QuestionResult>? {
        val response =
            ApiService.getQuestionStoryApiService().getAllPartQuestionResult(userId, subsectionId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }
}

