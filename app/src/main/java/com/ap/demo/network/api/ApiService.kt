package com.ap.demo.network.api

import com.ap.demo.network.Constants.IMAGE_DATA
import com.ap.demo.network.beans.ImageModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(IMAGE_DATA)
    suspend fun imageData(@Query("limit") page: Int ) : Response<ImageModelResponse>
}
