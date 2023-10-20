package com.inquirypro.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.ResetPasswordResponse
import com.inquirypro.model.registration.ConfirmationResponse
import com.inquirypro.repository.ResetPasswordRepository
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val resetPasswordRepository: ResetPasswordRepository) :
    ViewModel() {

    private val _confirmResponse = MutableLiveData<Result<ResetPasswordResponse>>()
    val confirmResponse: LiveData<Result<ResetPasswordResponse>> get() = _confirmResponse


    private val _resetPasswordResponse = MutableLiveData<Result<ResetPasswordResponse>>()
    val resetPasswordResponse: LiveData<Result<ResetPasswordResponse>> get() = _resetPasswordResponse

    fun resetPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                when (val response = resetPasswordRepository.resetPassword(email)) {
                    is Result.Success -> {
                        response.data.let {
                            _confirmResponse.postValue(Result.Success(it))
                        }
                    }

                    is Result.Error -> {
                        _confirmResponse.postValue(Result.Error(response.message))
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                Log.i("exception login", e.toString())
                _confirmResponse.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }

    fun setNewPassword(newPassword: String, email: String, resetToken: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                when (val response = resetPasswordRepository.setNewPassword(newPassword,email,resetToken)) {
                    is Result.Success -> {
                        response.data.let {
                            _resetPasswordResponse.postValue(Result.Success(it))
                        }
                    }

                    is Result.Error -> {
                        _resetPasswordResponse.postValue(Result.Error(response.message))
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                Log.i("exception login", e.toString())
                _resetPasswordResponse.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }

}