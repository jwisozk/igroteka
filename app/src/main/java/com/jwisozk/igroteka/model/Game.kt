package com.jwisozk.igroteka.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val thumbnail: String?
) : Parcelable