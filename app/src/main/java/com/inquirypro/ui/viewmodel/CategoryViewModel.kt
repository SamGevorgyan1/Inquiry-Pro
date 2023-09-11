package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Category
import com.inquirypro.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    private val _categoryList = MutableLiveData<List<Category>?>()
    val categoryList: LiveData<List<Category>?> get() = _categoryList


    fun getCategories(){
        viewModelScope.launch {
           _categoryList.value = categoryRepository.getCategories()
        }
    }
}