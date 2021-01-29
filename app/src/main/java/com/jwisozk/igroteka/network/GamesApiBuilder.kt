package com.jwisozk.igroteka.network

import android.content.res.Resources
import android.util.Log
import com.jwisozk.igroteka.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


class GamesApiBuilder(resources: Resources) {

    private val baseUrl = resources.getString(R.string.url_server_api)
    private val apiKey = resources.getString(R.string.api_key)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(createLoggingInterceptor())
        .addInterceptor(GamesApiKeyInterceptor(apiKey))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService: GamesApiService by lazy {
        retrofit.create()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLogger = HttpLoggingInterceptor.Logger { message ->
            Log.d(OkHttpClient::class.java.name, message)
        }
        val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}