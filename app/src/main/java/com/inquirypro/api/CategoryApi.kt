package com.inquirypro.api

import com.inquirypro.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {

    @GET("/categories")
    fun getAllCategories(): Call<List<Category>>
}