package com.example.galery.data.converter

import androidx.room.TypeConverter
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.data.entity.Urls
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGroupTaskMemberList(value: Urls?): String {
        val gson = Gson()
        val type = object : TypeToken<Urls>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList(value: String): Urls {
        val gson = Gson()
        val type = object : TypeToken<Urls>() {}.type
        return gson.fromJson(value, type)
    }
}