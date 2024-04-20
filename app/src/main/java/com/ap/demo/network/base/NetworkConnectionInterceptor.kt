package com.ap.demo.network.base

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor : Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!NetworkService.instance.isOnline()) throw IOException("No internet Connection")
        else return chain.proceed(request)
    }
}

