package com.jwisozk.igroteka.network

import com.squareup.moshi.Json

class SearchGameResponse(
    val count: Int,
    val next: String?,
    @Json(name = "results")
    val games: List<GameNetworkModel>
)