package com.inquirypro.api

import com.inquirypro.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {
    @GET("/api/article/get-all")
    fun getAllArticle(): Call<List<Article>>

    @GET("/api/article/get-by-id/{articleId}")
    fun getArticleById(@Path("articleId") id: Long): Call<Article>

}