package com.example.galery.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.galery.data.converter.Converters
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photo_table")

@Parcelize
data class OnePhotoItem(
    var alt_description: String? = "",
    var color: String = "",
    var description: String? = "",

    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @TypeConverters(Converters::class)
    var urls: Urls? = null,
    var isLiked: Boolean = false
) : Parcelable