package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.ApiService
import com.inquirypro.model.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface HistoryRepository {

    suspend fun getHistoriesByUserId(userId: Int): Result<List<History>?>

    suspend fun getHistoriesByUserIdAndSubsectionId(userId: Int, subsectionId: Int): Result<List<History>?>
}

class HistoryRepositoryImpl : HistoryRepository {

    override suspend fun getHistoriesByUserId(userId: Int): Result<List<History>?> {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    ApiService.getHistoryApiService().getUserHistories(userId).awaitResponse()

                if (response.isSuccessful) {
                    Result.Success(response.body())
                } else {
                    Result.Error("Failed to fetch stories: ${response.message()}")
                }
            } catch (e: Exception) {
                Result.Error("An error occurred: ${e.message}")
            }
        }
    }



    override suspend fun getHistoriesByUserIdAndSubsectionId(userId: Int, subsectionId: Int): Result<List<History>?> {
        return try {
            val response = ApiService.getHistoryApiService().getHistoriesByUserIdAndSubsectionId(userId, subsectionId).awaitResponse()

            if (response.isSuccessful) {
                Log.i("stories", response.body().toString())
                Result.Success(response.body())
            } else {
                Result.Error("Failed to fetch stories by user and category: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }
}
