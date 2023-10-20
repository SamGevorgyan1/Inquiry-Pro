package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.QuestionResult
import com.inquirypro.repository.QuestionResultRepository
import com.inquirypro.repository.Result
import kotlinx.coroutines.*

class QuestionResultViewModel(private val questionResultRepository: QuestionResultRepository) :
    ViewModel() {


    private val _userCorrectAnswers = MutableLiveData<List<QuestionResult>?>()
    val userCorrectAnswers: LiveData<List<QuestionResult>?> get() = _userCorrectAnswers

    private val _userIncorrectAnswers = MutableLiveData<List<QuestionResult>?>()
    val userIncorrectAnswers: LiveData<List<QuestionResult>?> get() = _userIncorrectAnswers


    suspend fun getQuestionResultByUserIdAndSubsectionId(
        userId: Int,
        subsectionId: Int
    ): List<QuestionResult>? {
        return coroutineScope {
            val asyncQuestionResult =
                async { questionResultRepository.getSubsectionQuestionResult(userId, subsectionId) }

            asyncQuestionResult.await()
        }
    }


    suspend fun getQuestionResultById(questionResultId: Int): QuestionResult? {
        return coroutineScope {
            val asyncCreateStory =
                async { questionResultRepository.getQuestionResultById(questionResultId) }

            val questionStory = asyncCreateStory.await()

            questionStory
        }
    }

    suspend fun createQuestionResult(questionResult: QuestionResult): QuestionResult? {
        return coroutineScope {
            val asyncCreateStory =
                async { questionResultRepository.createQuestionResult(questionResult) }

            val createdStory = asyncCreateStory.await()

            createdStory
        }
    }


    suspend fun getUserCorrectAnswerAmount(userId: Int): Int? {
        return coroutineScope {
            val asyncCorrectAnswers =
                async { questionResultRepository.getUserCorrectAnswerAmount(userId) }

            asyncCorrectAnswers.await()
        }
    }

    suspend fun getUserIncorrectAnswerAmount(userId: Int): Int? {
        return coroutineScope {
            val asyncIncorrectAnswers =
                async { questionResultRepository.getUserIncorrectAnswerAmount(userId) }

            asyncIncorrectAnswers.await()
        }
    }


    suspend fun getAllPartQuestionResult(userId: Int, subsectionId: Int): List<QuestionResult>? {
        return coroutineScope {
            val asyncPartQuestionResults =
                async { questionResultRepository.getAllPartQuestionResult(userId, subsectionId) }

            asyncPartQuestionResults.await()
        }
    }

    fun getUserCorrectAnswers(userId: Int) {
        viewModelScope.launch {

            when (val response = questionResultRepository.getUserCorrectAnswers(userId)) {
                is Result.Success -> {
                    response.data?.let {
                        _userCorrectAnswers.postValue(it)
                    }
                }

                is Result.Error -> {

                }
            }
        }
    }

    fun getUserIncorrectAnswers(userId: Int) {
        viewModelScope.launch {
            when (val response = questionResultRepository.getUserIncorrectAnswers(userId)) {
                is Result.Success -> {
                    response.data.let {
                        _userIncorrectAnswers.postValue(it)
                    }
                }
                is Result.Error -> {}
            }
        }
    }
}