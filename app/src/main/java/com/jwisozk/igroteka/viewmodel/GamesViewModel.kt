package com.jwisozk.igroteka.viewmodel

import androidx.lifecycle.*
import com.jwisozk.igroteka.repositories.GamesRepository
import kotlinx.coroutines.launch

class GamesViewModel(val gamesRepository: GamesRepository) : ViewModel() {

    private val _countGames = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            _countGames.value = gamesRepository.getCountGames()
        }
    }

    private val _searchState = MutableLiveData<SearchState>()
    private val _searchResult = MutableLiveData<GamesResult>()

    suspend fun sendSearchQuery(query: String) {
        viewModelScope.launch {
            _searchState.value = Loading
            if (query.trim().isEmpty()) {
                _searchResult.value = EmptyQuery
            } else {
                try {
                    val result = gamesRepository.searchGames(query)
                    if (result.isEmpty()) {
                        _searchResult.value = EmptyResult
                    } else {
                        _searchResult.value = ValidResult(result)
                    }
                } catch (e: Throwable) {
                    _searchResult.value = ErrorResult
                }
            }
            _searchState.value = Ready
        }
    }

    val searchResult: LiveData<GamesResult>
        get() = _searchResult

    val searchState: LiveData<SearchState>
        get() = _searchState

    val countGames: LiveData<Int>
        get() = _countGames

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repo: GamesRepository) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GamesViewModel(gamesRepository = repo) as T
        }
    }
}