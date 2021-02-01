package com.jwisozk.igroteka.model

class SearchGameResponse(
    val count: Int = 0,
    val games: List<Game> = emptyList()
) {

    fun getHintCountGames(count: Int, before: String, after: String): String =
        String.format("%s %s %s", before, String.format("%,d", count), after)
}
