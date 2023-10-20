package com.inquirypro.repository

import com.inquirypro.api.ApiService
import retrofit2.Response
import retrofit2.awaitResponse

interface LogoutRepository {

    suspend fun logout(): Result<Unit>

}

class LogoutRepoImpl : LogoutRepository {
    override suspend fun logout(): Result<Unit> {
        return try {
            val logoutResponse = ApiService

            val response: Response<Unit> =
                logoutResponse.getLogoutService().logout().awaitResponse()
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error("Logout failed with status code: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }
}