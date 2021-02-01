package com.jwisozk.igroteka.network

import okhttp3.Interceptor
import okhttp3.Response

class GamesApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter(API_KEY, apiKey)
            .build()

        val request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "key"
    }
}