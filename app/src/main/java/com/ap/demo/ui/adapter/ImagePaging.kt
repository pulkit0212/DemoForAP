package com.ap.demo.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ap.demo.network.api.ApiService
import com.ap.demo.network.beans.ImageModelResponse
import retrofit2.HttpException
import java.io.IOException

class ImagePaging(private val apiService: ApiService) : PagingSource<Int, ImageModelResponse.ImageModelResponseItem>() {

    private var STARTING_PAGE_INDEX = 1
    override fun getRefreshKey(state: PagingState<Int, ImageModelResponse.ImageModelResponseItem>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageModelResponse.ImageModelResponseItem> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiService.imageData(page)

            val data = response.body()
            if (!data.isNullOrEmpty()){
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page -1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )

            }else{
                return LoadResult.Error(NullPointerException("Data is Null or Empty"))
            }
        } catch (e : HttpException){
            return LoadResult.Error(e)
        } catch (e : IOException){
            return LoadResult.Error(e)
        } catch (e : Exception){
            return LoadResult.Error(e)
        }
    }

}