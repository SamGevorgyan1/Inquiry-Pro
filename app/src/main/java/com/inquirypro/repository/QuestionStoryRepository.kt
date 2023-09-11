package com.inquirypro.repository

import com.inquirypro.api.RetrofitService
import com.inquirypro.model.Question
import com.inquirypro.model.QuestionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface QuestionStoryRepository {

    suspend fun getQuestionStoriesByUserId(userId: Int): List<QuestionResult>?

    suspend fun getQuestionsFromQuestionStory(questionStoryId: Int): List<Question>?

    suspend fun getQuestionStoryById(questionStoryId: Int): QuestionResult?

    suspend fun createQuestionStory(questionResult: QuestionResult): QuestionResult?

    suspend fun createQuestionWithStory(questionResult: QuestionResult): QuestionResult?

    suspend fun getUserCorrectAnswers(userId: Int): List<QuestionResult>?

    suspend fun getUserIncorrectAnswers(userId: Int): List<QuestionResult>?

    suspend fun getUserCorrectAnswerAmount(userId: Int): Int?

    suspend fun getUserIncorrectAnswerAmount(userId: Int): Int?
}

class QuestionStoryRepositoryImpl : QuestionStoryRepository {

    override suspend fun getQuestionStoriesByUserId(userId: Int): List<QuestionResult>? {
        val response =
            RetrofitService.getQuestionStoryApiService().getQuestionStoryByUserId(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getQuestionsFromQuestionStory(questionStoryId: Int): List<Question>? {
        val response = RetrofitService.getQuestionStoryApiService()
            .getQuestionsFromQuestionStory(questionStoryId).awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getQuestionStoryById(questionStoryId: Int): QuestionResult? {
        val response =
            RetrofitService.getQuestionStoryApiService().getQuestionStoryById(questionStoryId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun createQuestionStory(questionResult: QuestionResult): QuestionResult? {

        return withContext(Dispatchers.IO) {
            try {
                val response =
                    RetrofitService.getQuestionStoryApiService().createQuestionStory(questionResult)
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

    override suspend fun createQuestionWithStory(questionResult: QuestionResult): QuestionResult? {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    RetrofitService.getQuestionStoryApiService()
                        .createQuestionWithStory(questionResult)
                        .awaitResponse()

                if (response.isSuccessful) {
                    response.body()
                } else {
                    null // Handle error case, returning null
                }
            } catch (e: Exception) {
                null // Handle exceptions, returning null
            }
        }
    }

    override suspend fun getUserCorrectAnswers(userId: Int): List<QuestionResult>? {
        val response =
            RetrofitService.getQuestionStoryApiService().getUserCorrectAnswers(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getUserIncorrectAnswers(userId: Int): List<QuestionResult>? {
        val response =
            RetrofitService.getQuestionStoryApiService().getUserIncorrectAnswers(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getUserCorrectAnswerAmount(userId: Int): Int? {
        val response =
            RetrofitService.getQuestionStoryApiService().getUserCorrectAnswerAmount(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getUserIncorrectAnswerAmount(userId: Int): Int? {
        val response =
            RetrofitService.getQuestionStoryApiService().getUserIncorrectAnswerAmount(userId)
                .awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }
}

/**
RetrofitService.getQuestionStoryApiService().createQuestionStory(questionResult).enqueue(
object:Callback<QuestionResult>{
override fun onResponse(call: Call<QuestionResult>, response: Response<QuestionResult>) {
if (response.isSuccessful){
Log.i("Create story","Successful")
return response.body()
}

}

override fun onFailure(call: Call<QuestionResult>, t: Throwable) {
Log.i("Response quest", "Failed")
}
}
)
 **/

