package com.jwisozk.igroteka.network

import com.jwisozk.igroteka.network.GameNetworkModel
import com.squareup.moshi.Json

class SearchGameResponse (
    val count: Int,
    val next: String?,
    val previous: String?,
    @Json(name = "results")
    val games: List<GameNetworkModel>
)