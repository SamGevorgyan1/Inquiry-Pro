package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.User
import com.inquirypro.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _loginResult = MutableLiveData<User?>()
    val loginResult: LiveData<User?> = _loginResult

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun loginUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.loginUser(user)
            _loginResult.postValue(result)
        }
    }

    suspend fun signUpUser(user: User): User {
        return userRepository.signUpUser(user)
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            _user.value = userRepository.getUserById(userId)
        }
    }
}