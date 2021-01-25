package com.jwisozk.igroteka.network

import com.squareup.moshi.Json

class SearchGameResponse(
    val next: String?,
    @Json(name = "results")
    val games: List<GameNetworkModel>
)