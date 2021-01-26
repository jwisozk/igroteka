package com.jwisozk.igroteka.repositories

import com.jwisozk.igroteka.model.Game
import com.jwisozk.igroteka.network.GamesApiBuilder
import com.jwisozk.igroteka.network.GamesApiService

class GamesRepository(private val gamesApiService: GamesApiService) {

    suspend fun searchGames(query: String, page: Int = 1): List<Game> {
        return gamesApiService
            .searchGame(apiKey = GamesApiBuilder.API_KEY, query = query, page = page)
            .games
            .map { Game(it.id, it.name, it.posterPath) }
            .toList()
    }

    suspend fun getCountGames(): Int {
        return gamesApiService
            .getCountGames(apiKey = GamesApiBuilder.API_KEY).count
    }
}