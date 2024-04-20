package com.ap.demo.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ap.demo.network.api.ApiService
import com.ap.demo.network.base.BaseDataSource
import com.ap.demo.network.beans.ImageModelResponse
import com.ap.demo.ui.adapter.ImagePaging
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(val apiService: ApiService):BaseDataSource(){

    fun getImagesApi(): Flow<PagingData<ImageModelResponse.ImageModelResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { ImagePaging(apiService) }
        ).flow
    }
}
