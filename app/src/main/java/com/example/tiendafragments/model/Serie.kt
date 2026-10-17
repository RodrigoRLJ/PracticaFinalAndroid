package com.example.tiendafragments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Serie(
    val id: Int = 0,
    val name: String = "",
    val summary: String? = null,
    val image: ImageModel? = null,
    val rating: RatingModel? = null,
    val genres: List<String> = emptyList(),
    val status: String? = null,
    val premiered: String? = null
) : Parcelable
