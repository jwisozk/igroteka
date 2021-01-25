package com.jwisozk.igroteka.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GamesApiBuilder {

    private val baseUrl = "https://api.rawg.io/api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService: GamesApiService by lazy {
        retrofit.create(GamesApiService::class.java)
    }
}