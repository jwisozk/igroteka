package com.jwisozk.igroteka.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("games")
    suspend fun searchGame(
        @Query("key") apiKey: String = "51801b6d654043e29e6544a9d728b636",
        @Query("search") query: String,
        @Query("page") page: Int = 1
    ): SearchGameResponse
}