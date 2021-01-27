package com.jwisozk.igroteka.network

import com.jwisozk.igroteka.model.Game
import com.jwisozk.igroteka.model.SearchGameResponse

class SearchGameResponseMapper {
    fun map(searchGameResponseNetworkModel: SearchGameResponseNetworkModel): SearchGameResponse {
        val games = searchGameResponseNetworkModel.games.map { mapGame(it) }
        return SearchGameResponse(
            count = searchGameResponseNetworkModel.count,
            games = games
        )
    }

    private fun mapGame(gameNetworkModel: GameNetworkModel): Game {
        return Game(
            id = gameNetworkModel.id,
            name = gameNetworkModel.name,
            thumbnail = gameNetworkModel.posterPath
        )
    }
}