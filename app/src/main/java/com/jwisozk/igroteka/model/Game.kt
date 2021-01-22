package com.jwisozk.igroteka.model

import com.squareup.moshi.Json

data class Game(
    val id: Int,
    val name: String,
//    @Json(name = "background_image") val thumbnail: String?
    val thumbnail: String?
)