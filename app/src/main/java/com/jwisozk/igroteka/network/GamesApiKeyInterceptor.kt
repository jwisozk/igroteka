package com.jwisozk.igroteka.network

import okhttp3.Interceptor
import okhttp3.Response

class GamesApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .addHeader(API_KEY_HEADER, GamesApiBuilder.API_KEY)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY_HEADER = "x-api-key"
    }
}