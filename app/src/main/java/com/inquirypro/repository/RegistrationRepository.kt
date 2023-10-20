package com.inquirypro.repository

import com.inquirypro.api.ApiService
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.model.registration.ConfirmationResponse
import com.inquirypro.model.registration.RegistrationRequest
import com.inquirypro.model.registration.RegistrationRequestGoogle
import com.inquirypro.model.registration.RegistrationResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.awaitResponse

interface RegistrationRepository {
    suspend fun registerEmail(request: RegistrationRequest): Result<RegistrationResponse>

    suspend fun registerWithGoogle(request: RegistrationRequestGoogle): Result<RegistrationResponse>

    suspend fun confirmToken(token: String, confirmCode: String): Result<ConfirmationResponse>
}


class RegistrationRepositoryImpl : RegistrationRepository {
    override suspend fun registerEmail(request: RegistrationRequest): Result<RegistrationResponse> {
        return try {
            val registrResponse =
                ApiService.getRegistrationApiService().registerWithEmail(request).awaitResponse()

            if (registrResponse.isSuccessful) {
                registrResponse.body()?.let { response ->
                    Result.Success(response)
                }
                    ?: Result.Error("Registration failed with response code: ${registrResponse.code()}")
            } else {
                try {
                    val errorBodyJson = JSONObject(registrResponse.errorBody()?.string())
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

    override suspend fun registerWithGoogle(request: RegistrationRequestGoogle): Result<RegistrationResponse> {
        return try {
            val registrResponse =
                ApiService.getRegistrationApiService().registerWithGoogle(request).awaitResponse()

            if (registrResponse.isSuccessful) {
                registrResponse.body()?.let { response ->
                    Result.Success(response)
                }
                    ?: Result.Error("Registration failed with response code: ${registrResponse.code()}")
            } else {
                try {
                    val errorBodyJson = JSONObject(registrResponse.errorBody()?.string())
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

    override suspend fun confirmToken(
        token: String,
        confirmCode: String
    ): Result<ConfirmationResponse> {


        return try {
            val registrResponse =
                ApiService.getRegistrationApiService().confirmToken(token, confirmCode)
                    .awaitResponse()

            if (registrResponse.isSuccessful) {
                registrResponse.body()?.let { response ->
                    Result.Success(response)
                }
                    ?: Result.Error("Confirm failed with response code: ${registrResponse.code()}")
            } else {
                try {
                    val errorBodyJson = JSONObject(registrResponse.errorBody()?.string())
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