package com.inquirypro.api


import com.inquirypro.model.Story
import com.inquirypro.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitService {

    private lateinit var retrofit: Retrofit

    private val okHttpClient = OkHttpClient.Builder()
        //.addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
        return retrofit
    }

    fun getUserApiService(): UserSignInApi = getClient().create(UserSignInApi::class.java)
    fun getCategoryApiService(): CategoryApi = getClient().create(CategoryApi::class.java)
    fun getPartApiService(): PartApi = getClient().create(PartApi::class.java)
    fun getQuestionApiService(): QuestionApi = getClient().create(QuestionApi::class.java)
    fun getQuestionStoryApiService(): QuestionStoryApi = getClient().create(QuestionStoryApi::class.java)
    fun getStoryApiService(): StoryApi = getClient().create(StoryApi::class.java)
    fun getEmployeeApiService(): EmployeeApi = getClient().create(EmployeeApi::class.java)


}