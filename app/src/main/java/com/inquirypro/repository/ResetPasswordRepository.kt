package com.inquirypro.repository

import com.inquirypro.api.ApiService
import com.inquirypro.model.ResetPasswordResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.awaitResponse

interface ResetPasswordRepository {

    suspend fun resetPassword(email: String): Result<ResetPasswordResponse>?

    suspend fun setNewPassword(
        newPassword: String, email: String, resetToken: String
    ): Result<ResetPasswordResponse>?
}


class ResetPasswordImpl : ResetPasswordRepository {
    override suspend fun resetPassword(email: String): Result<ResetPasswordResponse>? {
        return try {
            val resetPasswordResponse =
                ApiService.getResetPasswordApi().resetPassword(email).awaitResponse()

            if (resetPasswordResponse.isSuccessful) {
                resetPasswordResponse.body()?.let { response ->
                    Result.Success(response)
                }
                    ?: Result.Error("Reset password failed with response code: ${resetPasswordResponse.code()}")
            } else {
                try {
                    val errorBodyJson = JSONObject(resetPasswordResponse.errorBody()?.string())
                    val errorMessage = errorBodyJson.optString("message", "Unknown error")
                    Result.Error(errorMessage)
                } catch (e: JSONException) {
                    Result.Error("Failed to parse error message")
                }
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun setNewPassword(
        newPassword: String,
        email: String,
        resetToken: String
    ): Result<ResetPasswordResponse>? {
        return try {
            val resetPasswordResponse =
                ApiService.getResetPasswordApi().setNewPassword(newPassword, email, resetToken).awaitResponse()

            if (resetPasswordResponse.isSuccessful) {
                resetPasswordResponse.body()?.let { response ->
                    Result.Success(response)
                }
                    ?: Result.Error("Reset password failed with response code: ${resetPasswordResponse.code()}")
            } else {
                try {
                    val errorBodyJson = JSONObject(resetPasswordResponse.errorBody()?.string())
                    val errorMessage = errorBodyJson.optString("message", "Unknown error")
                    Result.Error(errorMessage)
                } catch (e: JSONException) {
                    Result.Error("Failed to parse error message")
                }
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }
}