package com.inquirypro.api

import com.inquirypro.model.Category
import retrofit2.Call
import retrofit2.http.GET


interface CategoryApi {
    @GET("/api/category/all-categories")
    fun getAllCategories(): Call<List<Category>>
}