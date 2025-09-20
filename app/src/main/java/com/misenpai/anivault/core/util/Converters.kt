package com.misenpai.anivault.core.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.misenpai.anivault.domain.model.WatchStatus

class Converters {
    @TypeConverter
    fun fromWatchStatus(status: WatchStatus): String = status.name

    @TypeConverter
    fun toWatchStatus(status: String): WatchStatus = WatchStatus.valueOf(status)

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(it, type)
        }
    }
}