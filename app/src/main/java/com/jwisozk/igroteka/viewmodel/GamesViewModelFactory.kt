package com.jwisozk.igroteka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jwisozk.igroteka.repositories.GamesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("UNCHECKED_CAST")
class GamesViewModelFactory(private val repo: GamesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @ExperimentalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass){
            GamesViewModel::class.java -> GamesViewModel(gamesRepository = repo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}