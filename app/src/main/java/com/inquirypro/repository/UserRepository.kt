package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.RetrofitService
import com.inquirypro.model.User
import retrofit2.awaitResponse

interface UserRepository {
    suspend fun loginUser(user: User): User?
    suspend fun signUpUser(user: User): User
    suspend fun getUserById(userId:Int):User?
}

class UserRepositoryImpl : UserRepository {

    override suspend fun loginUser(user: User): User? {
        val response = RetrofitService.getUserApiService().loginUser(user).awaitResponse()
        Log.i("User successful", response.body().toString())
        if (response.isSuccessful) return response.body()

        return null
    }

    override suspend fun signUpUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: Int): User? {
        val response = RetrofitService.getUserApiService().getUserById(userId).awaitResponse()

        if (response.isSuccessful) return  response.body()

        return null

    }
}