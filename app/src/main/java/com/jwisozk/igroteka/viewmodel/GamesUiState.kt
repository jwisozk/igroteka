package com.jwisozk.igroteka.viewmodel

import com.jwisozk.igroteka.model.SearchGameResponse

sealed class GamesUiState {
    data class Success(val searchGameResponse: SearchGameResponse): GamesUiState()
    data class Error(val exception: Throwable): GamesUiState()
    data class Loading(val isLoading: Boolean): GamesUiState()
}

