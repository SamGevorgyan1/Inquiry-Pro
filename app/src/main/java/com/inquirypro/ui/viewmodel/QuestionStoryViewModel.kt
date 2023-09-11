package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Question
import com.inquirypro.model.QuestionResult
import com.inquirypro.repository.QuestionStoryRepository
import kotlinx.coroutines.*

class QuestionStoryViewModel(private val questionStoryRepository: QuestionStoryRepository) :
    ViewModel() {

    private val _questionResultList = MutableLiveData<List<QuestionResult>?>()
    val questionResultList: LiveData<List<QuestionResult>?> get() = _questionResultList

    private val _questionStoryQuestions = MutableLiveData<List<Question>?>()
    val questionStoryQuestions: LiveData<List<Question>?> get() = _questionStoryQuestions

    private val _questionResult = MutableLiveData<QuestionResult>()
    val questionResult: LiveData<QuestionResult> get() = _questionResult

    private val _createdQuestionResult = MutableLiveData<QuestionResult>()
    val createdQuestionResult: LiveData<QuestionResult> = _createdQuestionResult

    private val _userCorrectAnswers = MutableLiveData<List<QuestionResult>?>()
    val userCorrectAnswers:LiveData<List<QuestionResult>?> get() = _userCorrectAnswers

    private val _userIncorrectAnswers = MutableLiveData<List<QuestionResult>?>()
    val userIncorrectAnswers:LiveData<List<QuestionResult>?> get() = _userIncorrectAnswers


    fun getQuestionStoryByUserId(userId: Int) {
        viewModelScope.launch {
            _questionResultList.value = questionStoryRepository.getQuestionStoriesByUserId(userId)
        }
    }

    fun getQuestionStoryQuestions(questionId: Int) {
        viewModelScope.launch {
            _questionStoryQuestions.value =
                questionStoryRepository.getQuestionsFromQuestionStory(questionId)
        }
    }

    suspend fun getQuestionStoryById(questionStoryId: Int): QuestionResult? {
        return coroutineScope {
            val asyncCreateStory =
                async { questionStoryRepository.getQuestionStoryById(questionStoryId) }

            val questionStory = asyncCreateStory.await()

            questionStory
        }
    }

    suspend fun createQuestionStory(questionResult: QuestionResult): QuestionResult? {
        return coroutineScope {
            val asyncCreateStory =
                async { questionStoryRepository.createQuestionStory(questionResult) }

            val createdStory = asyncCreateStory.await()

            createdStory
        }
    }


    fun getUserCorrectAnswers(userId: Int){
        viewModelScope.launch {
            _userCorrectAnswers.value = questionStoryRepository.getUserCorrectAnswers(userId)
        }
    }

    fun getUserIncorrectAnswers(userId: Int){
        viewModelScope.launch {
            _userIncorrectAnswers.value = questionStoryRepository.getUserIncorrectAnswers(userId)
        }
    }

    suspend fun getUserCorrectAnswerAmount(userId: Int): Int? {
        return coroutineScope {
            val asyncCorrectAnswers =
                async { questionStoryRepository.getUserCorrectAnswerAmount(userId) }

            asyncCorrectAnswers.await()
        }
    }

    suspend fun getUserIncorrectAnswerAmount(userId: Int): Int? {
        return coroutineScope {
            val asyncIncorrectAnswers =
                async { questionStoryRepository.getUserIncorrectAnswerAmount(userId) }

            asyncIncorrectAnswers.await()
        }
    }
}