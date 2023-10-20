package com.inquirypro.api

import com.inquirypro.model.login.LoginResponse
import com.inquirypro.util.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(createAuthInterceptor())
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()
    }

    private fun createAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ${getAuthToken()}")
                .build()
            chain.proceed(request)
        }
    }

    private fun getAuthToken(): String {
        val loginResponse = LoginResponse.retrieveLoginResponse()
        return loginResponse?.token.orEmpty()
    }

    private val loginApiService: LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }

    private val registrationApi: RegistrationApi by lazy {
        retrofit.create(RegistrationApi::class.java)
    }

    private val logoutApi: LogoutApi by lazy {
        retrofit.create(LogoutApi::class.java)
    }

    private val resetPasswordApiService: ResetPasswordApi by lazy {
        retrofit.create(ResetPasswordApi::class.java)
    }

    private val categoryApi: CategoryApi by lazy {
        retrofit.create(CategoryApi::class.java)
    }

    private val partApi: PartApi by lazy {
        retrofit.create(PartApi::class.java)
    }

    private val questionApi: QuestionApi by lazy {
        retrofit.create(QuestionApi::class.java)
    }

    private val questionResultApi: QuestionResultApi by lazy {
        retrofit.create(QuestionResultApi::class.java)
    }

    private val historyApi: HistoryApi by lazy {
        retrofit.create(HistoryApi::class.java)
    }

    private val articleApi: ArticleApi by lazy {
        retrofit.create(ArticleApi::class.java)
    }


    fun getRegistrationApiService(): RegistrationApi = registrationApi

    fun getLoginApi(): LoginApi = loginApiService

    fun getLogoutService(): LogoutApi = logoutApi

    fun getResetPasswordApi(): ResetPasswordApi = resetPasswordApiService

    fun getCategoryApiService(): CategoryApi = categoryApi

    fun getPartApiService(): PartApi = partApi

    fun getQuestionApiService(): QuestionApi = questionApi

    fun getQuestionStoryApiService(): QuestionResultApi = questionResultApi

    fun getHistoryApiService(): HistoryApi = historyApi

    fun getArticleApiService(): ArticleApi = articleApi
}