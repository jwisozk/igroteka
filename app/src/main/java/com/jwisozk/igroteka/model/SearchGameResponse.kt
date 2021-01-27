package com.jwisozk.igroteka.model

class SearchGameResponse(
    val count: Int = 0,
    val games: List<Game> = emptyList()
)