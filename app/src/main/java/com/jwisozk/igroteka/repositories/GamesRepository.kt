package com.jwisozk.igroteka.repositories

import android.util.Log
import com.jwisozk.igroteka.model.Game
import com.jwisozk.igroteka.network.GamesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class GamesRepository(private val gamesApiService: GamesApiService) {

    @FlowPreview
    internal suspend fun searchGames(query: String, page: Int = 1): List<Game> {
        return withContext(Dispatchers.IO) {
            flowOf(
                gamesApiService.searchGame(query = query, page = page)
            )
        }
            .flowOn(Dispatchers.IO)
            .onEach { Log.d(GamesRepository::class.java.name, it.games.toString()) }
            .flatMapMerge { it.games.asFlow() }
            .map { Game(it.id, it.name, it.posterPath) }
            .toList()
    }
}