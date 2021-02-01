package com.jwisozk.igroteka.viewmodel

import androidx.lifecycle.*
import com.jwisozk.igroteka.repositories.GamesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class GamesViewModel(val gamesRepository: GamesRepository) : ViewModel() {

    private val _gamesUiState = MutableStateFlow<GamesUiState?>(null)
    val gamesUiState: StateFlow<GamesUiState?> = _gamesUiState

    var isCountGamesReceive = false

    init {
        viewModelScope.launch {
            sendSearchGamesQuery("")
        }
    }

    suspend fun sendSearchGamesQuery(
        query: String
    ) {
        _gamesUiState.value = GamesUiState.Loading(false)
        try {
            _gamesUiState.value = GamesUiState.Success(gamesRepository.searchGames(query, 1))
        } catch (e: Throwable) {
            _gamesUiState.value = GamesUiState.Error(e)
        }
        _gamesUiState.value = GamesUiState.Loading(true)
    }
}