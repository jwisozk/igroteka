package com.jwisozk.igroteka.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigator
import com.jwisozk.igroteka.network.GamesApiBuilder
import com.jwisozk.igroteka.repositories.GamesRepository
import com.jwisozk.igroteka.viewmodel.GamesViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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