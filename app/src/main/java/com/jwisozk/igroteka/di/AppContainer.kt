package com.jwisozk.igroteka.di

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jwisozk.igroteka.network.GamesApiBuilder
import com.jwisozk.igroteka.network.GamesApiTalker
import com.jwisozk.igroteka.network.SearchGameResponseMapper
import com.jwisozk.igroteka.repositories.GamesRepository
import com.jwisozk.igroteka.viewmodel.GamesViewModel
import com.jwisozk.igroteka.viewmodel.GamesViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AppContainer(resources: Resources) {

    private val gamesRepo: GamesRepository

    init {
        val gamesApiBuilder = GamesApiBuilder(resources)
        val gamesApiTalker = GamesApiTalker(gamesApiBuilder.retrofitService)
        val searchGameResponseMapper = SearchGameResponseMapper()
        gamesRepo = GamesRepository(gamesApiTalker, searchGameResponseMapper)
    }

    @ExperimentalCoroutinesApi
    fun getGamesViewModel(fragment: Fragment): GamesViewModel {
        return ViewModelProvider(
            fragment,
            GamesViewModelFactory(gamesRepo)
        ).get(GamesViewModel::class.java)
    }
}