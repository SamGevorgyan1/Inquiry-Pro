package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Story
import com.inquirypro.repository.StoryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _userStories = MutableLiveData<List<Story>>()
    val userStories: LiveData<List<Story>> get() = _userStories


    fun getStoriesByUSerId(userId: Int) {
        viewModelScope.launch {
            _userStories.value = storyRepository.getStoriesByUserId(userId)
        }
    }

    suspend fun createQuestionStory(story: Story): Story? {
        return coroutineScope {
            val asyncCreateStory = async { storyRepository.createQuestionStory(story) }
            val createdStory = asyncCreateStory.await()
            createdStory
        }
    }
}