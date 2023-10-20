package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.repository.LogoutRepository
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogoutViewModel(private val logoutRepository: LogoutRepository) : ViewModel() {


    private val _logoutResponse = MutableLiveData<Result<Unit>>()

    val logoutResponse: LiveData<Result<Unit>> get() = _logoutResponse


    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = logoutRepository.logout()) {

                is Result.Success -> {
                    _logoutResponse.postValue(Result.Success(response.data))
                    LoginResponse.saveLoginResponse(LoginResponse(null,null,null))
                }

                is Result.Error -> {
                    _logoutResponse.postValue(Result.Error(response.message))
                }
            }
        }
    }
}