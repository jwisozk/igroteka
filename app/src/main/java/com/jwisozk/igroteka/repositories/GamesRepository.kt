package com.jwisozk.igroteka.repositories

import com.jwisozk.igroteka.model.SearchGameResponse
import com.jwisozk.igroteka.network.GamesApiBuilder
import com.jwisozk.igroteka.network.GamesApiService
import com.jwisozk.igroteka.network.SearchGameResponseMapper

class GamesRepository(private val gamesApiService: GamesApiService) {

    suspend fun searchGames(query: String = "", page: Int = 1): SearchGameResponse {
        val searchGameResponseNetworkModel = gamesApiService
            .searchGame(apiKey = GamesApiBuilder.API_KEY, query = query, page = page)
        return SearchGameResponseMapper().map(searchGameResponseNetworkModel)
    }
}