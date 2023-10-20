package com.inquirypro.repository

import com.inquirypro.api.ApiService
import com.inquirypro.model.Part
import retrofit2.awaitResponse

interface PartRepository {

    suspend fun getPartsByCategoryId(categoryId: Int): Result<List<Part>>
    suspend fun getPartById(partId: Int): Result<Part>
}

class PartRepositoryImpl : PartRepository {

    override suspend fun getPartsByCategoryId(categoryId: Int): Result<List<Part>> {
        return try {
            val response =
                ApiService.getPartApiService().getPartsByCategoryId(categoryId).awaitResponse()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("Failed to fetch parts by category")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    override suspend fun getPartById(partId: Int): Result<Part> {
        return try {
            val response = ApiService.getPartApiService().getPartById(partId).awaitResponse()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: Part())
            } else {
                Result.Error("Failed to fetch part by ID")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }
}