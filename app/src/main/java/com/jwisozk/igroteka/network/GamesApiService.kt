package com.jwisozk.igroteka.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("games")
    suspend fun searchGame(
        @Query(API_KEY) apiKey: String? = null,
        @Query("search") query: String,
        @Query("page") page: Int,
    ): SearchGameResponseNetworkModel

    companion object {
        const val API_KEY = "key"
    }
}