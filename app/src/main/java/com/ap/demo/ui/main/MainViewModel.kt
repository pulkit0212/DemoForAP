package com.ap.demo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ap.demo.network.repository.ApiHelperImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiHelper: ApiHelperImpl): ViewModel()  {

    fun getImagesList() = apiHelper.getImagesApi().cachedIn(viewModelScope)
}