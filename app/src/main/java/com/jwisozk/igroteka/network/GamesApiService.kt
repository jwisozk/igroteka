package com.jwisozk.igroteka.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("games")
    suspend fun searchGame(
        @Query("search") query: String,
        @Query("page") page: Int,
    ): SearchGameResponseNetworkModel

}