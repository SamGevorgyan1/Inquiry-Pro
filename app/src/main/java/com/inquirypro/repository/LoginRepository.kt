package com.inquirypro.repository

import android.util.Log
import com.inquirypro.api.ApiService
import com.inquirypro.model.login.LoginRequest
import com.inquirypro.model.login.LoginResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import retrofit2.awaitResponse

interface LoginRepository {

    suspend fun login(loginRequest: LoginRequest, isGoogleLogin: Boolean = false): Result<LoginResponse?>


}

class LoginRepositoryImpl : LoginRepository {

    override suspend fun login(
        loginRequest: LoginRequest,
        isGoogleLogin: Boolean
    ): Result<LoginResponse?> {

        return try {
            val loginResponse = ApiService

            val response: Response<LoginResponse> = if (isGoogleLogin) {
                loginResponse.getLoginApi().loginWithGoogle(loginRequest).awaitResponse()
            } else {
                loginResponse.getLoginApi().login(loginRequest).awaitResponse()
            }

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    LoginResponse.saveLoginResponse(responseBody)
                    Log.i("response in google", response.body().toString())
                    Result.Success(responseBody)

                } ?: Result.Error(
                    if (isGoogleLogin) "Google login failed" else "Login failed"
                )
            } else {
                try {
                    val errorBodyJson = JSONObject(response.errorBody()?.string())
                    val errorMessage = errorBodyJson.optString("message", "Unknown error")
                    Result.Error(errorMessage)
                } catch (e: JSONException) {
                    Result.Error(
                        if (isGoogleLogin) "Failed to parse Google login error message"
                        else "Failed to parse error message"
                    )
                }
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }

    }
}
