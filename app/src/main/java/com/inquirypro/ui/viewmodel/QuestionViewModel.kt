package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Question
import com.inquirypro.repository.QuestionRepository
import kotlinx.coroutines.launch
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers


class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    private val _questionLiveData = MutableLiveData<List<Question>?>()
    val questionLiveData: LiveData<List<Question>?> get() = _questionLiveData

    private val  _partQuestionLiveData = MutableLiveData<List<Question>?>()
    val partQuestionLiveData: LiveData<List<Question>?> get() = _partQuestionLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String?>()
    val errorLiveData: LiveData<String?> get() = _errorLiveData

    fun getQuestionByCategoryId(partId: Int) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            val result = questionRepository.getQuestionsByCategoryId(partId)
            _loadingLiveData.value = false

            when (result) {
                is Result.Success -> _questionLiveData.value = result.data
                is Result.Error -> _errorLiveData.value = result.message
            }
        }
    }

    fun getQuestionsBySubsectionId(subsectionId: Int){
        viewModelScope.launch(Dispatchers.Main) {
            _loadingLiveData.value = true
            val result = questionRepository.getQuestionsBySubsectionId(subsectionId)
            _loadingLiveData.value = false

            when (result) {
                is Result.Success -> _partQuestionLiveData.value = result.data
                is Result.Error -> _errorLiveData.value = result.message
            }
        }
    }
}
