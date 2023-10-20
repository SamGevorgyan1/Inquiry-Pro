package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Part
import com.inquirypro.repository.PartRepository
import kotlinx.coroutines.launch
import com.inquirypro.repository.Result


class PartViewModel(private val partRepository: PartRepository) : ViewModel() {

    private val _partsLiveData = MutableLiveData<List<Part>?>()
    val partsLiveData: LiveData<List<Part>?> = _partsLiveData

    private val _partLiveData = MutableLiveData<Part?>()
    val partLiveData: LiveData<Part?> = _partLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String?>()
    val errorLiveData: LiveData<String?> = _errorLiveData

    fun getPartsByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            val result = partRepository.getPartsByCategoryId(categoryId)
            _loadingLiveData.value = false

            when (result) {
                is Result.Success -> _partsLiveData.value = result.data
                is Result.Error -> _errorLiveData.value = result.message
            }
        }
    }

    fun getPartById(partId: Int) {
        viewModelScope.launch {
            _loadingLiveData.value = true
            val result = partRepository.getPartById(partId)
            _loadingLiveData.value = false

            when (result) {
                is Result.Success -> _partLiveData.value = result.data
                is Result.Error -> _errorLiveData.value = result.message
            }
        }
    }
}
