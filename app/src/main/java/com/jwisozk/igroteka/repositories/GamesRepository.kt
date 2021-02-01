package com.jwisozk.igroteka.repositories

import com.jwisozk.igroteka.model.SearchGameResponse
import com.jwisozk.igroteka.network.GamesApiTalker
import com.jwisozk.igroteka.network.SearchGameResponseMapper

class GamesRepository(
    private val gamesApiTalker: GamesApiTalker,
    private val searchGameResponseMapper: SearchGameResponseMapper
) {

    suspend fun searchGames(query: String, page: Int): SearchGameResponse =
        searchGameResponseMapper.map(gamesApiTalker.searchGames(query, page))

    // .flow(init)
    // .catch() -> отлавливать ошибки
}