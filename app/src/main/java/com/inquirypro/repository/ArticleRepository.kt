package com.inquirypro.repository

import com.inquirypro.api.ApiService
import com.inquirypro.exception.ArticleException
import com.inquirypro.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface ArticleRepository {

    suspend fun getAllArticle(): Result<List<Article>?>

    suspend fun getArticleById(id: Long): Article?

}

class ArticleRepositoryImpl : ArticleRepository {
    override suspend fun getAllArticle(): Result<List<Article>?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiService.getArticleApiService().getAllArticle().awaitResponse()
                if (response.isSuccessful) {
                    Result.Success(response.body())
                } else {
                    Result.Error("Failed to fetch stories: ${response.code()}")
                }
            } catch (e: Exception) {
                Result.Error("An error occurred: ${e.message}")
            }
        }
    }

    override suspend fun getArticleById(id: Long): Article? {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiService.getArticleApiService().getArticleById(id).awaitResponse()

                if (response.isSuccessful) {
                    response.body() ?: throw ArticleException("Article is null")
                } else {
                    throw ArticleException("Failed to fetch article: ${response.code()}")
                }
            } catch (e: Exception) {
                throw ArticleException("An error occurred: ${e.message}")
            }
        }
    }

}