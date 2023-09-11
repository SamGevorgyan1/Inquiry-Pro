package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.RetrofitService
import com.inquirypro.model.Category
import retrofit2.awaitResponse

interface CategoryRepository {

    suspend fun getCategories(): List<Category>?

}

class CategoryRepositoryImpl() : CategoryRepository {

    override suspend fun getCategories(): List<Category>? {
        val response = RetrofitService.getCategoryApiService().getAllCategories().awaitResponse()
        Log.i("Category successful", response.body().toString())
        if (response.isSuccessful) return response.body()

        return null
    }
}