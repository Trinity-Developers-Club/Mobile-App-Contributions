package com.example.intuitivecats.breeds.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
) : Parcelable