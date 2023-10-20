package com.inquirypro.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.registration.ConfirmationResponse
import com.inquirypro.model.registration.RegistrationRequest
import com.inquirypro.model.registration.RegistrationRequestGoogle
import com.inquirypro.model.registration.RegistrationResponse
import com.inquirypro.repository.RegistrationRepository
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationRepository: RegistrationRepository):ViewModel() {

    private val _registerResponse = MutableLiveData<Result<RegistrationResponse>>()
    val registerResponse: LiveData<Result<RegistrationResponse>> get() = _registerResponse

    private val _confirmResponse = MutableLiveData<Result<ConfirmationResponse>>()
    val confirmResponse: LiveData<Result<ConfirmationResponse>> get() = _confirmResponse



    fun register(registrationRequest: RegistrationRequest) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                when (val response = registrationRepository.registerEmail(registrationRequest)) {
                    is Result.Success -> {
                        response.data.let {
                            _registerResponse.postValue(Result.Success(it))
                        }
                    }
                    is Result.Error -> {
                        _registerResponse.postValue(Result.Error(response.message))
                    }
                }
            } catch (e: Exception) {
                Log.i("exception login", e.toString())
                _registerResponse.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }

    fun registerWithGoogle(registrationRequest: RegistrationRequestGoogle){

        viewModelScope.launch(Dispatchers.IO) {
            try {

                when (val response = registrationRepository.registerWithGoogle(registrationRequest)) {
                    is Result.Success -> {
                        response.data.let {
                            _registerResponse.postValue(Result.Success(it))
                        }
                    }
                    is Result.Error -> {
                        _registerResponse.postValue(Result.Error(response.message))
                    }
                }
            } catch (e: Exception) {
                Log.i("exception login", e.toString())
                _registerResponse.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }

    fun confirmCode(token:String,confirmCode:String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                when (val response = registrationRepository.confirmToken(token, confirmCode)) {
                    is Result.Success -> {
                        response.data.let {
                            _confirmResponse.postValue(Result.Success(it))
                        }
                    }
                    is Result.Error -> {
                        _confirmResponse.postValue(Result.Error(response.message))
                    }
                }
            } catch (e: Exception) {
                Log.i("exception login", e.toString())
                _confirmResponse.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }
}