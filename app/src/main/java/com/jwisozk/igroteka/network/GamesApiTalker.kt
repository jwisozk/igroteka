package com.jwisozk.igroteka.network

class GamesApiTalker(private val gamesApiService: GamesApiService) {

    suspend fun searchGames(query: String, page: Int): SearchGameResponseNetworkModel =
        gamesApiService.searchGame(query = query, page = page)
}