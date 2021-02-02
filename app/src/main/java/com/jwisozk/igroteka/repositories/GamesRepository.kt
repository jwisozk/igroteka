package com.jwisozk.igroteka.repositories

import com.jwisozk.igroteka.network.GamesApiTalker
import com.jwisozk.igroteka.network.SearchGameResponseMapper
import com.jwisozk.igroteka.viewmodel.GamesUiState
import com.jwisozk.igroteka.viewmodel.GamesUiStateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GamesRepository(
    private val gamesApiTalker: GamesApiTalker,
    private val searchGameResponseMapper: SearchGameResponseMapper
) {

    suspend fun searchGames(query: String, page: Int): Flow<GamesUiStateWrapper> =
        flow {
            emit(GamesUiStateWrapper(GamesUiState.Loading(false)))
            val searchGameResponse =
                searchGameResponseMapper.map(gamesApiTalker.searchGames(query, page))
            emit(GamesUiStateWrapper(GamesUiState.Success(searchGameResponse)))
        }.catch { exception ->
            emit(GamesUiStateWrapper(GamesUiState.Error(exception)))
        }.flowOn(Dispatchers.IO)

}