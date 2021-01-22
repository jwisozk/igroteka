package com.jwisozk.igroteka.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jwisozk.igroteka.model.Game
import com.jwisozk.igroteka.network.SearchGameResponse
import com.jwisozk.igroteka.repositories.GamesRepository
import kotlinx.coroutines.launch

class GamesViewModel(val gamesRepository: GamesRepository) : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private val _properties = MutableLiveData<SearchGameResponse>()

    val properties: LiveData<SearchGameResponse>
        get() = _properties

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getGamesProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getGamesProperties() {
        viewModelScope.launch {
            try {
                _properties.value = GamesApi.retrofitService.getProperties()
                _response.value = "Success: Mars properties retrieved"
                Log.d("debug", "onResponse: count ${properties.value?.count}")
                Log.d("debug", "onResponse: next ${properties.value?.next}")
                Log.d("debug", "onResponse: previous ${properties.value?.previous}")
                Log.d("debug", "onResponse: resukts ${properties.value?.results}")
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
                Log.d("debug", "Failure: ${e.message}")
            }
        }
//        GamesApi.retrofitService.getProperties().enqueue(
//            object : Callback<List<MarsProperty>> {
//                override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
//                    _response.value =
//                        "Success: ${response.body()?.size} Mars properties retrieved"
//                    Log.d("debug", "onResponse: ${response.body()?.size}")
//                }
//
//                override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                    _response.value = "Failure: " + t.message
//                }
//
//            })
    }

    fun onGameAction(game: Game) {
//        navigator.navigateTo("https://www.themoviedb.org/movie/${game.id}")
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repo: GamesRepository) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GamesViewModel(gamesRepository = repo) as T
        }
    }
}