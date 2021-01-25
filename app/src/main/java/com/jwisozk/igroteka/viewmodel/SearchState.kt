package com.jwisozk.igroteka.viewmodel

sealed class SearchState
object Loading : SearchState()
object Ready : SearchState()