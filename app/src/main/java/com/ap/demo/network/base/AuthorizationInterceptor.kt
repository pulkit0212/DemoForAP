package com.ap.demo.network.base

import com.ap.demo.utils.PrefManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest() =
        newBuilder()
            .header("token", PrefManager.getString("apiToken").toString())
            .build()
}