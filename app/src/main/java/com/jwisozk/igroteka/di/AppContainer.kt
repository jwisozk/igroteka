package com.jwisozk.igroteka.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jwisozk.igroteka.network.GamesApiBuilder
import com.jwisozk.igroteka.repositories.GamesRepository
import com.jwisozk.igroteka.viewmodel.GamesViewModel

class AppContainer {

    private val gamesRepo: GamesRepository

    init {
        val gamesApiBuilder = GamesApiBuilder()
        gamesRepo = GamesRepository(gamesApiBuilder.retrofitService)
    }

    fun getGamesViewModel(fragment: Fragment): GamesViewModel {
        return ViewModelProvider(
            fragment,
            GamesViewModel.Factory(gamesRepo)
        ).get(GamesViewModel::class.java)
    }
}