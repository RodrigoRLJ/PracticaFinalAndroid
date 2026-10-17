package com.example.tiendafragments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    val medium: String? = null,
    val original: String? = null
) : Parcelable
