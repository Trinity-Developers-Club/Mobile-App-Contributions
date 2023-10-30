package com.example.intuitivecats.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.intuitivecats.network.CatRepository

import com.example.pagerv.paging.PageRv
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val catRepository: CatRepository) : ViewModel() {

    fun getBreedById(id: String?) = catRepository.getBreedById(id)

    val dataList = PageRv(catRepository,25).data.cachedIn(viewModelScope)
}
