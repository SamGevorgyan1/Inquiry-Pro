package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Question
import com.inquirypro.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    private val _questionLiveData = MutableLiveData<List<Question>?>()
    val questionLiveData: LiveData<List<Question>?> get() = _questionLiveData

    fun getQuestionByCategoryId(partId: Int) {
        viewModelScope.launch {
            _questionLiveData.value = questionRepository.getQuestionsByCategoryId(partId)
        }
    }
}