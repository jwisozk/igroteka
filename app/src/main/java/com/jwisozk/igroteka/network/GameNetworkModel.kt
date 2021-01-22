package com.jwisozk.igroteka.network

import com.squareup.moshi.Json

class GameNetworkModel(
    val id: Int,
    val name: String,
    @Json(name = "background_image")
    val posterPath: String?
)