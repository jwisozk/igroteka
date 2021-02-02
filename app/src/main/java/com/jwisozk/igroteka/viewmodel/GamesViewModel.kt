package com.jwisozk.igroteka.viewmodel

import androidx.lifecycle.*
import com.jwisozk.igroteka.repositories.GamesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class GamesViewModel(val gamesRepository: GamesRepository) : ViewModel() {

    private val _gamesUiState = MutableStateFlow<GamesUiState?>(null)
    val gamesUiState: StateFlow<GamesUiState?> = _gamesUiState

    var hintSearch: String? = null

    @InternalCoroutinesApi
    suspend fun sendSearchGamesQuery(query: String, page: Int = 1) {
        viewModelScope.launch {
            gamesRepository.searchGames(query, page).collect { gamesUiStateWrapper ->
                val gamesUiState = gamesUiStateWrapper.gamesUiState
                _gamesUiState.value = when (gamesUiState) {
                    is GamesUiState.Success -> GamesUiState.Success(gamesUiState.searchGameResponse)
                    is GamesUiState.Error -> GamesUiState.Error(gamesUiState.exception)
                    is GamesUiState.Loading -> GamesUiState.Loading(gamesUiState.isLoading)
                }
            }
        }
    }
}