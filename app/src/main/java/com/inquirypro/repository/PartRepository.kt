package com.inquirypro.repository

import com.inquirypro.api.RetrofitService
import com.inquirypro.model.Part
import retrofit2.awaitResponse

interface PartRepository {

    suspend fun getPartsByCategoryId(categoryId: Int): List<Part>?
    suspend fun getPartById(partId: Int): Part?
}

class PartRepositoryImpl : PartRepository {


    override suspend fun getPartsByCategoryId(categoryId: Int): List<Part>? {
        val response =
            RetrofitService.getPartApiService().getPartsByCategoryId(categoryId).awaitResponse()
        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun getPartById(partId: Int): Part? {
        val response = RetrofitService.getPartApiService().getPartById(partId).awaitResponse()

        if (response.isSuccessful) return response.body()

        return null
    }
}