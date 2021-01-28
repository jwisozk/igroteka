package com.jwisozk.igroteka.viewmodel

import androidx.lifecycle.*
import com.jwisozk.igroteka.model.SearchGameResponse
import com.jwisozk.igroteka.repositories.GamesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class GamesViewModel(val gamesRepository: GamesRepository) : ViewModel() {

    private val gamesUiStatePlug: GamesUiState = GamesUiState.Success(SearchGameResponse())

    private val _countGamesUiState = MutableStateFlow(gamesUiStatePlug)
    val countGamesUiState: StateFlow<GamesUiState> = _countGamesUiState

    private val _gamesUiState = MutableStateFlow(gamesUiStatePlug)
    val gamesUiState: StateFlow<GamesUiState> = _gamesUiState

    init {
        viewModelScope.launch {
            sendSearchGamesQuery("", _countGamesUiState)
        }
    }

    suspend fun sendSearchGamesQuery(
        query: String,
        _uiState: MutableStateFlow<GamesUiState> = _gamesUiState
    ) {
        _uiState.value = GamesUiState.Loading(false)
        try {
            _uiState.value = GamesUiState.Success(gamesRepository.searchGames(query, 1))
        } catch (e: Throwable) {
            _uiState.value = GamesUiState.Error(e)
        }
        _uiState.value = GamesUiState.Loading(true)
    }
}