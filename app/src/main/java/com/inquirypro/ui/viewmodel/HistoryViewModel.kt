package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.History
import com.inquirypro.repository.HistoryRepository
import kotlinx.coroutines.launch
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers


class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    private val _userStories = MutableLiveData<List<History>?>()
    val userStories: LiveData<List<History>?> get() = _userStories


    fun getHistoriesByUserId(userId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = historyRepository.getHistoriesByUserId(userId)
            if (result is Result.Success) {
                _userStories.value = result.data
            } else if (result is Result.Error) {

                _userStories.value = emptyList()
            }
        }
    }


    suspend fun getHistoriesByUserIdAndSubsectionId(userId: Int, subsectionId: Int): List<History>? {
        val result = historyRepository.getHistoriesByUserIdAndSubsectionId(userId, subsectionId)
        if (result is Result.Success) {
            return result.data
        } else if (result is Result.Error) {

            return emptyList()
        }
        return null
    }
}
