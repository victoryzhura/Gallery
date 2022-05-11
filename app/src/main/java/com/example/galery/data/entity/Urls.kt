package com.example.galery.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls(
    var full: String?,
    var regular: String?,
    var small: String?
): Parcelable