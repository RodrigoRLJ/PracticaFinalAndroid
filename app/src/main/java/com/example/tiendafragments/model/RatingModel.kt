package com.example.tiendafragments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingModel(
    val average: Double? = null
) : Parcelable
