package com.inquirypro.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inquirypro.model.Part
import com.inquirypro.repository.PartRepository
import kotlinx.coroutines.launch

class PartViewModel(private val partRepository: PartRepository) : ViewModel() {

    private val _partsLiveData = MutableLiveData<List<Part>?>()
    val partsLiveData: LiveData<List<Part>?> = _partsLiveData

    private val _partLiveData = MutableLiveData<Part?>()
    val partLiveData: LiveData<Part?> = _partLiveData


    fun getPartsByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            _partsLiveData.value = partRepository.getPartsByCategoryId(categoryId)
        }
    }

    fun getPartById(partId: Int) {
        viewModelScope.launch {
            _partLiveData.value = partRepository.getPartById(partId)
        }
    }
}