package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.RetrofitService
import com.inquirypro.model.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface StoryRepository {

    suspend fun getStoriesByUserId(userId: Int): List<Story>?
    suspend fun createQuestionStory(story: Story): Story?

}

class StoryRepositoryImpl : StoryRepository {

    override suspend fun getStoriesByUserId(userId: Int): List<Story>? {
        val response = RetrofitService.getStoryApiService().getUserStories(userId).awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun createQuestionStory(story: Story): Story? {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    RetrofitService.getStoryApiService().addQuestionResultToStory(story)
                        .awaitResponse()

                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.i("response not",response.message())
                   null
                }
            } catch (e: Exception) {
                null // Handle exceptions, returning null
            }
        }
    }
}