package com.inquirypro.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.login.LoginRequest
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.repository.LoginRepository
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<Result<LoginResponse>>()
    val loginResponse: LiveData<Result<LoginResponse>> get() = _loginResponse

    fun login(loginRequest: LoginRequest, isGoogleLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                when (val response = loginRepository.login(loginRequest, isGoogleLogin)) {
                    is Result.Success -> {

                        response.data?.let {
                            _loginResponse.postValue(Result.Success(it))
                        } ?: run {
                            _loginResponse.postValue(Result.Error("Login failed: Data is null"))
                        }
                    }
                    is Result.Error -> {

                        _loginResponse.postValue(Result.Error(response.message))
                    }
                }
            } catch (e: Exception) {
                Log.i("exception login", e.toString())
                _loginResponse.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }
}