package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Article
import com.inquirypro.repository.ArticleRepository
import com.inquirypro.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _articleLiveData = MutableLiveData<List<Article>?>()
    val articleLiveData: LiveData<List<Article>?> get() = _articleLiveData


    fun getAllArticle() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    articleRepository.getAllArticle()
                }
                when (result) {
                    is Result.Success -> _articleLiveData.value = result.data
                    is Result.Error -> _articleLiveData.value = emptyList()
                    else -> {}
                }
            } catch (e: Exception) {
                _articleLiveData.value = emptyList()
            }
        }
    }

    suspend fun getArticleById(id: Long): Article? {
        return articleRepository.getArticleById(id)
    }
}