package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.ApiService
import com.inquirypro.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface CategoryRepository {
    suspend fun getCategories(): List<Category>?
}

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun getCategories(): List<Category>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiService.getCategoryApiService().getAllCategories().awaitResponse()
                Log.i("Category successful", response.body().toString())
                if (response.isSuccessful) {
                    return@withContext response.body()
                } else {
                    Log.i("Category not successful", response.code().toString())
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("Category error", e.message.toString())
                return@withContext null
            }
        }
    }
}